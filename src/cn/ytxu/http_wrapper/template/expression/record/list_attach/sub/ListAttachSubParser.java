package cn.ytxu.http_wrapper.template.expression.record.list_attach.sub;

import cn.ytxu.http_wrapper.template.expression.record.text.TextExpressionRecord;

import java.util.List;

/**
 * Created by ytxu on 2016/12/27.
 */
public class ListAttachSubParser {

    private final List<String> subContents;
    private TextExpressionRecord textStartRecord, textTempRecord, textEndRecord;

    public ListAttachSubParser(List<String> subContents) {
        this.subContents = subContents;
    }

    public ListAttachSubParser parse() {
        textStartRecord = ListAttachSubExpression.text_start.getTextER(subContents);
        textTempRecord = ListAttachSubExpression.list_temp.getTextER(subContents);
        textEndRecord = ListAttachSubExpression.text_end.getTextER(subContents);
        return this;
    }

    public TextExpressionRecord getTextStartRecord() {
        return textStartRecord;
    }

    public TextExpressionRecord getTextTempRecord() {
        return textTempRecord;
    }

    public TextExpressionRecord getTextEndRecord() {
        return textEndRecord;
    }

}
