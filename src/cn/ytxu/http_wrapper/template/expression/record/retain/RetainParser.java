package cn.ytxu.http_wrapper.template.expression.record.retain;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.common.enums.RetainType;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * 自动生成文件中需要保留的数据:有可能是我自己后面加的,也有可能是其他coder加的<br>
 * 例如:简化网络请求中参数的长度<br>
 * 2016-04-18<br>
 * version v6
 */
public class RetainParser {

    private final File targetFile;
    private final String fileCharset;

    public RetainParser(String dirPath, String fileName, String fileCharset) {
        this.targetFile = new File(dirPath, fileName);
        this.fileCharset = fileCharset;
    }

    /**
     * 1、先要判断目标文件是否存在；<br>
     * 2、若存在，在判断该文件是否有数据；<br>
     * 3、若有数据，则打开输入流，获取到以上几个需要保留（retain）的数据；-->输出为一个对象<br>
     * 4、在解析文档对象，并输出文件数据时，将几个分类的数据，插入其中；
     */
    public RetainModel start() {
        if (!targetFile.exists()) {
            return RetainModel.getEmpty();
        }

        try {
            List<String> contents = FileUtil.getLineContents(targetFile.getAbsolutePath(), fileCharset);
            return parserAndGetRetain(contents);
        } catch (IOException e) {
            e.printStackTrace();
            return RetainModel.getEmpty();
        }
    }

    private static RetainModel parserAndGetRetain(List<String> contents) throws IOException {
        RetainModel retain = new RetainModel();
        Iterator<String> contentIter = contents.iterator();

        while (contentIter.hasNext()) {
            String strLine = contentIter.next();
            if (!strLine.contains(RetainType.StartTag)) {
                continue;
            }

            RetainType retainType = RetainType.getByTag(strLine);
            StringBuffer retainContent = getretainContent(contentIter);
            retainType.appendRetainContent(retain, retainContent);
        }

        return retain;
    }

    private static StringBuffer getretainContent(Iterator<String> contentIter) {
        StringBuffer retainDataBuffer = new StringBuffer();
        while (contentIter.hasNext()) {
            String retainData = contentIter.next();
            if (retainData.contains(RetainType.EndTag)) {
                break;
            }
            retainDataBuffer.append(retainData).append("\n");
        }
        return retainDataBuffer;
    }

}