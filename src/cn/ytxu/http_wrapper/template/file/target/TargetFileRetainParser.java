package cn.ytxu.http_wrapper.template.file.target;

import cn.ytxu.http_wrapper.common.enums.RetainType;
import cn.ytxu.http_wrapper.template.expression.record.retain.RetainModel;

import java.util.Iterator;
import java.util.List;

/**
 * 自动生成文件中需要保留的数据:有可能是我自己后面加的,也有可能是其他coder加的<br>
 * 例如:简化网络请求中参数的长度<br>
 * 2016-04-18<br>
 * version v6
 */
public class TargetFileRetainParser {

    private final List<String> targetFileContents;

    public TargetFileRetainParser(List<String> targetFileContents) {
        this.targetFileContents = targetFileContents;
    }

    /**
     * 1、先要判断目标文件是否存在；<br>
     * 2、若存在，在判断该文件是否有数据；<br>
     * 3、若有数据，则打开输入流，获取到以上几个需要保留（retain）的数据；-->输出为一个对象<br>
     * 4、在解析文档对象，并输出文件数据时，将几个分类的数据，插入其中；
     */
    public RetainModel start() {
        RetainModel retain = new RetainModel();
        Iterator<String> contentIter = targetFileContents.iterator();

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

    private StringBuffer getretainContent(Iterator<String> contentIter) {
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