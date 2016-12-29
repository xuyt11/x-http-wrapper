package cn.ytxu.http_wrapper.template.engine.parser;

import cn.ytxu.http_wrapper.template.file.model.XHWTFileModel;
import cn.ytxu.http_wrapper.template.file.model.XHWTModel;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/8/28.
 * contents 进入之后,会抽离其中的header部分,剩下temp文件的内容，最后返回XTempModel
 */
public class XHWTFileContentParser {

    private final List<String> contents;
    private final XHWTModel model = new XHWTModel();

    public XHWTFileContentParser(List<String> contents) {
        this.contents = contents;
    }

    public XHWTModel start() {
        checkFirstLine();

        String header = getHeaderAndRemoveItInContent();
        XHWTFileModel xTempFile = new Gson().fromJson(header, XHWTFileModel.class);
        model.setFile(xTempFile);

        model.setContents(contents);
        return model;
    }

    private void checkFirstLine() {
        String fistLineContent = contents.get(0).trim();
        if (!XHWTModel.HeaderStartTag.equals(fistLineContent)) {
            throw new TheFirstLineMustBeHeaderStartTagLineException(fistLineContent);
        }
    }

    private static final class TheFirstLineMustBeHeaderStartTagLineException extends RuntimeException {
        public TheFirstLineMustBeHeaderStartTagLineException(String fistLineContent) {
            super("the fist line is " + fistLineContent);
        }
    }

    private String getHeaderAndRemoveItInContent() {
        contents.remove(0);
        StringBuilder headerBuilder = new StringBuilder();
        Iterator<String> iterator = contents.iterator();
        while (iterator.hasNext()) {
            String content = iterator.next();

            if (XHWTModel.HeaderEndTag.equals(content.trim())) {
                iterator.remove();
                break;
            }

            headerBuilder.append(content);
            iterator.remove();
        }
        return headerBuilder.toString();
    }

}
