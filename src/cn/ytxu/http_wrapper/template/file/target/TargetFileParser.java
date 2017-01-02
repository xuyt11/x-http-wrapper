package cn.ytxu.http_wrapper.template.file.target;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.util.MD5FileUtil;
import cn.ytxu.http_wrapper.template.expression.record.retain.RetainModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2017/1/2.
 */
public class TargetFileParser {

    private final File targetFile;// 目标文件
    private final String fileCharset;// 目标文件的字符编码
    private final boolean isExists;// 目标文件是否存在

    private RetainModel retain;
    private String targetFileMD5 = null;// 目标文件的MD5

    public TargetFileParser(String dirPath, String fileName, String fileCharset) {
        this.targetFile = new File(dirPath, fileName);
        this.fileCharset = fileCharset;
        this.isExists = targetFile.exists();
    }

    public TargetFileParser start() {
        if (!isExists) {
            retain = RetainModel.getEmpty();
            return this;
        }

        final List<String> targetFileContents;
        try {
            targetFileContents = FileUtil.getLineContents(targetFile.getAbsolutePath(), fileCharset);
        } catch (IOException e) {
            e.printStackTrace();
            retain = RetainModel.getEmpty();
            return this;
        }

        retain = new RetainParser(targetFileContents).start();
        parseTargetFileMD5(targetFileContents);
        return this;
    }

    private void parseTargetFileMD5(List<String> targetFileContents) {
        StringBuilder targetFileContentBuilder = new StringBuilder();
        for (String targetFileContent : targetFileContents) {
            targetFileContentBuilder.append(targetFileContent).append("\n");
        }
        targetFileMD5 = MD5FileUtil.getMD5String(targetFileContentBuilder.toString());
    }

    public RetainModel getRetain() {
        return retain;
    }

    public boolean needWriteNewContent(String newContent) {
        final boolean needWriteNewContent;
        final String newMD5;

        if (!isExists) {
            newMD5 = null;
            needWriteNewContent = true;
        } else {
            newMD5 = MD5FileUtil.getMD5String(newContent);
            needWriteNewContent = Objects.isNull(targetFileMD5) || !targetFileMD5.equals(newMD5);
        }

        log(newMD5, needWriteNewContent);
        return needWriteNewContent;
    }

    private void log(String newMD5, boolean needWriteNewContent) {
        LogUtil.i(TargetFileParser.class, "{isExists=" + isExists +
                "\t needWriteNewContent=" + needWriteNewContent + "\t targetFile=" + targetFile.getName() +
                "\t targetFileMD5=" + targetFileMD5 + "\t newMD5=" + newMD5 + "}");
    }
}
