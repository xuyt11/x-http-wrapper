package cn.ytxu.http_wrapper.template.expression.record.list_attach.attr;

import java.util.regex.Matcher;

/**
 * ListAttachExpression的属性枚举
 */
public enum ListAttachAttr {
    each("需要循环的list,获取反射方法的名称", "( each=\"\\w+\")", " each=\"", "\""),
    attach("需要在contents中替换的字符串", "( attach=\"\\w+\")", " attach=\"", "\"");

    private static final String TEXT_START = "<t:list_attach";
    private static final String TEXT_END = ">";

    private final String regex;
    private final String front;
    private final String end;

    ListAttachAttr(String tag, String regex, String front, String end) {
        this.regex = regex;
        this.front = front;
        this.end = end;
    }

    public static String getAttachPatternText() {
        String attachPatternText = TEXT_START;
        for (ListAttachAttr attr : ListAttachAttr.values()) {
            attachPatternText += attr.regex;
        }
        return attachPatternText + TEXT_END;
    }


    public String getContent(Matcher matcher) {
        String group = attrGroupContent(matcher);

        int beginIndex = front.length();
        int endIndex = group.length() - end.length();

        String attrRealContent = group.substring(beginIndex, endIndex);
        return attrRealContent;
    }

    protected String attrGroupContent(Matcher matcher) {
        return matcher.group(attrGroupIndex());
    }

    protected int attrGroupIndex() {
        return ordinal() + 1;
    }

}