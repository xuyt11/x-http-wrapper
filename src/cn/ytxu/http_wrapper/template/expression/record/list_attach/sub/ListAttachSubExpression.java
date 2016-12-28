package cn.ytxu.http_wrapper.template.expression.record.list_attach.sub;

import cn.ytxu.http_wrapper.template.expression.record.text.TextExpressionRecord;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ListAttachExpression的子表达式枚举
 * tip: 必须要有list_temp子表达式
 */
public enum ListAttachSubExpression {
    /**
     * 数据填充后，插入到首位
     */
    text_start("<t:list_attach text_start=\"([\\p{Print}\\p{Space}]+)\"/>"),
    /**
     * 遍历的数据模板，必须要有list_temp子表达式
     */
    list_temp("<t:list_attach list_temp=\"([\\p{Print}\\p{Space}]+)\"/>") {
        @Override
        protected String getContent(List<String> subContents) {
            String listTempContent = findTargetSubContent(subContents);
            return getContent(listTempContent);
        }
    },
    /**
     * 数据填充后，添加到末尾
     */
    text_end("<t:list_attach text_end=\"([\\p{Print}\\p{Space}]+)\"/>");

    private final Pattern pattern;

    ListAttachSubExpression(String regex) {
        this.pattern = Pattern.compile(regex);
    }


    public TextExpressionRecord getTextER(List<String> subContents) {
        String content = getContent(subContents);
        TextExpressionRecord textExpressionRecord = new TextExpressionRecord(content);
        textExpressionRecord.parseRecordAndSubRecords();
        return textExpressionRecord;
    }

    protected String getContent(List<String> subContents) {
        try {
            String subContent = findTargetSubContent(subContents);
            return getContent(subContent);
        } catch (NotFindTargetSubContentException e) {
            return "";
        }
    }

    protected String findTargetSubContent(List<String> subContents) {
        for (String subContent : subContents) {
            Matcher matcher = pattern.matcher(subContent);
            if (!matcher.find()) {
                continue;
            }
            return subContent;
        }
        throw new NotFindTargetSubContentException();
    }

    protected String getContent(String subContent) {
        Matcher matcher = pattern.matcher(subContent);
        matcher.find();
        return matcher.group(1);
    }

    private static final class NotFindTargetSubContentException extends RuntimeException {
    }

}
