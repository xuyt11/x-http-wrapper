package cn.ytxu.http_wrapper.template.expression.record.list_attach.attr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/12/27.
 */
public class ListAttachAttrParser {

    private final Pattern pattern;
    private final String startLineContent;

    private String methodName;
    private String attach;

    public ListAttachAttrParser(Pattern pattern, String startLineContent) {
        this.pattern = pattern;
        this.startLineContent = startLineContent;
    }

    public ListAttachAttrParser parse() {
        Matcher matcher = pattern.matcher(startLineContent);
        matcher.find();

        methodName = ListAttachAttr.each.getContent(matcher);
        attach = ListAttachAttr.attach.getContent(matcher);

        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getAttach() {
        return attach;
    }

}
