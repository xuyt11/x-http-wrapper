package cn.ytxu.http_wrapper.template.expression.record.list_attach.sub;

import java.util.List;

/**
 * Created by ytxu on 2016/12/27.
 */
public class ListAttachSubParser {

    private final List<String> subContents;
    private final ListAttachSubRecordEntity subRecord = new ListAttachSubRecordEntity();

    public ListAttachSubParser(List<String> subContents) {
        this.subContents = subContents;
    }

    public ListAttachSubRecordEntity parse() {
        for (ListAttachSubExpression listAttachSubExpression : ListAttachSubExpression.values()) {
            listAttachSubExpression.setThisExpressionRecord(subRecord, listAttachSubExpression.getTextER(subContents));
        }
        return subRecord;
    }

}
