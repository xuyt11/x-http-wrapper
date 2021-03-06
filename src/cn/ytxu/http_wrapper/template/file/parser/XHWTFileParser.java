package cn.ytxu.http_wrapper.template.file.parser;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.config.property.template_file_info.TemplateFileInfoWrapper;
import cn.ytxu.http_wrapper.template.file.converter.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.file.type.XHWTFileType;
import cn.ytxu.http_wrapper.template.file.model.XHWTModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ytxu on 2016/7/17.
 * 模板工具类
 */
public class XHWTFileParser {

    private final XHWTFileType xhwtFileType;
    private List<String> filePaths;// 模板文件的路径

    public XHWTFileParser(XHWTFileType xhwtFileType) {
        this.xhwtFileType = xhwtFileType;
    }

    public List<XHWTModel> start() throws XHWTNonNeedParsedException, IOException {
        try {
            this.filePaths = ConfigWrapper.getTemplateFileInfo().getTemplateFileAbsolutePaths(xhwtFileType);
        } catch (TemplateFileInfoWrapper.NonNeedParseTheTemplateFileException e) {
            throw new XHWTNonNeedParsedException(xhwtFileType);
        }

        List<XHWTModel> xhwtModels = new ArrayList<>(filePaths.size());
        for (String filePath : filePaths) {
            xhwtModels.add(getXhwtModel(filePath));
        }
        return xhwtModels;
    }

    private XHWTModel getXhwtModel(String filePath) throws IOException {
        List<String> contents = FileUtil.getLineContents(filePath, "UTF-8");
        XHWTModel model = new XHWTFileContentParser(contents).start();
        List<ExpressionRecord> records = parseStatementRecordsByXHWTModel(model);
        model.setRecords(records);
        return model;
    }

    public static final class XHWTNonNeedParsedException extends Exception {
        public XHWTNonNeedParsedException(XHWTFileType xhwtFileType) {
            super("this template type not need parsed, the type is " + xhwtFileType.name());
        }
    }

    private List<ExpressionRecord> parseStatementRecordsByXHWTModel(XHWTModel model) {
        List<ExpressionRecord> records = new Content2ExpressionRecordConverter.Top(model.getContents()).start();
        ExpressionRecord.parseRecords(records);
        return records;
    }

}
