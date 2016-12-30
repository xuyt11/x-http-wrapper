package cn.ytxu.http_wrapper.template.expression.record.list_attach.attr;

import java.util.regex.Matcher;

/**
 * ListAttachExpression的属性枚举
 */
public enum ListAttachAttr {
    /**
     * 需要循环的list,获取反射方法的名称
     */
    each(" each=\"(\\w+)\""),
    /**
     * 需要在contents中替换的字符串
     */
    attach(" attach=\"(\\w+)\"");

    private static final String TEXT_START = "<t:list_attach";
    private static final String TEXT_END = ">";

    private final String regex;

    ListAttachAttr(String regex) {
        this.regex = regex;
    }


    public static String getAttachPatternText() {
        StringBuffer attachPatternTextBuffer = new StringBuffer();
        attachPatternTextBuffer.append(TEXT_START);
        for (ListAttachAttr attr : ListAttachAttr.values()) {
            attachPatternTextBuffer.append(attr.regex);
        }
        attachPatternTextBuffer.append(TEXT_END);
        return attachPatternTextBuffer.toString();
    }


    public String getContent(Matcher matcher) {
        return matcher.group(attrGroupIndex());
    }

    protected int attrGroupIndex() {
        return ordinal() + 1;
    }

}