package cn.ytxu.http_wrapper.template.expression.record.list_attach.sub;

import cn.ytxu.http_wrapper.template.expression.record.text.TextExpressionRecord;

/**
 * Created by ytxu on 2017/02/20.
 */
public class ListAttachSubRecordEntity {

    static final TextExpressionRecord EMPTY = new TextExpressionRecord("");

    private TextExpressionRecord textStartRecord = EMPTY;
    private TextExpressionRecord listTempStartRecord = EMPTY;
    private TextExpressionRecord listTempRecord = EMPTY;
    private TextExpressionRecord listTempEndRecord = EMPTY;
    private TextExpressionRecord textEndRecord = EMPTY;

    void setTextStartRecord(TextExpressionRecord textStartRecord) {
        this.textStartRecord = textStartRecord;
    }

    void setListTempStartRecord(TextExpressionRecord listTempStartRecord) {
        this.listTempStartRecord = listTempStartRecord;
    }

    void setListTempRecord(TextExpressionRecord listTempRecord) {
        this.listTempRecord = listTempRecord;
    }

    void setListTempEndRecord(TextExpressionRecord listTempEndRecord) {
        this.listTempEndRecord = listTempEndRecord;
    }

    void setTextEndRecord(TextExpressionRecord textEndRecord) {
        this.textEndRecord = textEndRecord;
    }

    TextExpressionRecord getTextStartRecord() {
        return textStartRecord;
    }

    TextExpressionRecord getListTempStartRecord() {
        return listTempStartRecord;
    }

    TextExpressionRecord getListTempRecord() {
        return listTempRecord;
    }

    TextExpressionRecord getListTempEndRecord() {
        return listTempEndRecord;
    }

    TextExpressionRecord getTextEndRecord() {
        return textEndRecord;
    }

}
