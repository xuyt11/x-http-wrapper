package cn.ytxu.http_wrapper.template;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.record.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template.file.target.TargetFileParser;
import cn.ytxu.http_wrapper.template.file.type.XHWTFileType;
import cn.ytxu.http_wrapper.template.file.model.XHWTModel;
import cn.ytxu.http_wrapper.template.file.parser.XHWTFileParser;

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
            List<XHWTModel> xhwtModels = getXHWTModelsByParseTemplateFiles(xhwtFileType);

            List<? extends BaseModel> reflectDatas = xhwtFileType.getReflectiveDatas(versions);

            for (XHWTModel xhwtModel : xhwtModels) {
                for (BaseModel reflectData : reflectDatas) {
                    generateTargetFile(xhwtModel, reflectData);
                }
            }

            LogUtil.i(XHWTFileType.class, "this template type has been successfully parsed, the type is " + xhwtFileType.name());
        } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
            LogUtil.i(XHWTFileType.class, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<XHWTModel> getXHWTModelsByParseTemplateFiles(XHWTFileType xhwtFileType) throws XHWTFileParser.XHWTNonNeedParsedException, IOException {
        return new XHWTFileParser(xhwtFileType).start();
    }

    private void generateTargetFile(XHWTModel tModel, BaseModel reflectData) {
        final String dirPath = getString(tModel.getFileDir(), reflectData);
        final String fileName = getString(tModel.getFileName(), reflectData);
        final String fileCharset = ConfigWrapper.getBaseConfig().getCreateFileCharset();

        TargetFileParser parser = new TargetFileParser(dirPath, fileName, fileCharset).start();
        final String newContent = ExpressionRecord.getWriteBuffer(tModel.getRecords(), reflectData, parser.getRetain()).toString();

        if (parser.needWriteNewContent(newContent)) {
            writeNewContent2TargetFile(dirPath, fileName, fileCharset, newContent);
        }
    }

    private String getString(String content, BaseModel reflectModel) {
        TextExpressionRecord record = new TextExpressionRecord(content);
        record.parseRecordAndSubRecords();
        return record.getWriteBuffer(reflectModel, null).toString().trim();
    }

    private void writeNewContent2TargetFile(String dirPath, String fileName, String fileCharset, String newContent) {
        Writer writer = null;
        try {
            writer = FileUtil.getWriter(dirPath, fileName, fileCharset);
            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeWriter(writer);
        }
    }

}
