package cn.ytxu.http_wrapper.template;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.record.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template.file.type.XHWTFileType;
import cn.ytxu.http_wrapper.template.file.model.XHWTModel;
import cn.ytxu.http_wrapper.template.file.parser.XHWTFileParser;
import cn.ytxu.http_wrapper.template.expression.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template.expression.record.retain.RetainParser;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class XHWTemplateEngine {

    private final List<VersionModel> versions;

    public XHWTemplateEngine(List<VersionModel> versions) {
        this.versions = versions;
    }

    public void start() {
        for (XHWTFileType xhwtFileType : XHWTFileType.values()) {
            createTargetFile(xhwtFileType);
        }
    }

    private void createTargetFile(XHWTFileType xhwtFileType) {
        try {
            XHWTModel tModel = getXHWTModelByParseTemplateFile(xhwtFileType);

            List<? extends BaseModel> reflectDatas = xhwtFileType.getReflectiveDatas(versions);

            for (BaseModel reflectData : reflectDatas) {
                generateTargetFile(tModel, reflectData);
            }

            LogUtil.i(XHWTFileType.class, "this template type has been successfully parsed, the type is " + xhwtFileType.name());
        } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
            LogUtil.i(XHWTFileType.class, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XHWTModel getXHWTModelByParseTemplateFile(XHWTFileType xhwtFileType) throws XHWTFileParser.XHWTNonNeedParsedException, IOException {
        return new XHWTFileParser(xhwtFileType).start();
    }

    private void generateTargetFile(XHWTModel tModel, BaseModel reflectData) {
        String dirPath = getString(tModel.getFileDir(), reflectData);
        String fileName = getString(tModel.getFileName(), reflectData);

        String fileCharset = ConfigWrapper.getBaseConfig().getCreateFileCharset();
        RetainModel retain = new RetainParser(dirPath, fileName, fileCharset).start();

        Writer writer = null;
        try {
            writer = FileUtil.getWriter(dirPath, fileName, fileCharset);
            StringBuffer contentBuffer = ExpressionRecord.getWriteBuffer(tModel.getRecords(), reflectData, retain);
            writer.write(contentBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeWriter(writer);
        }
    }

    private String getString(String content, BaseModel reflectModel) {
        TextExpressionRecord record = new TextExpressionRecord(content);
        record.parseRecordAndSubRecords();
        return record.getWriteBuffer(reflectModel, null).toString().trim();
    }

}
