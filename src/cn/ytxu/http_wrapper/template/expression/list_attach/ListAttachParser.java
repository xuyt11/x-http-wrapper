package cn.ytxu.http_wrapper.template.expression.list_attach;

import cn.ytxu.http_wrapper.template.expression.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.helper.PatternHelper;

import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/12/27.
 */
public class ListAttachParser {

    private String startLineContent;
    private String methodName, attach;
    private TextExpressionRecord listTextRecord;

    private enum Attr {
        each("需要循环的list",
                new PatternHelper.PatternModel("each=\"", "\"",
                        Pattern.compile("(each=\")\\w+(\")"))),
        attach("需要在contents中替换的字符串",
                new PatternHelper.PatternModel("attach=\"", "\"",
                        Pattern.compile("(attach=\")\\w+(\")"))),
        list_text("遍历的数据模板",
                new PatternHelper.PatternModel("list_text=\"", "\"/>",
                        Pattern.compile("(list_text=\")[\\p{Print}\\p{Space}]+(\"/>)")));

        private final String tag;
        private final PatternHelper.PatternModel patternModel;

        Attr(String tag, PatternHelper.PatternModel patternModel) {
            this.tag = tag;
            this.patternModel = patternModel;
        }
    }

    public ListAttachParser(String startLineContent) {
        this.startLineContent = startLineContent;
    }

    public void parse() {
        methodName = PatternHelper.getPatternValue(Attr.each.patternModel, startLineContent);
        attach = PatternHelper.getPatternValue(Attr.attach.patternModel, startLineContent);

        String listText = PatternHelper.getPatternValue(Attr.list_text.patternModel, startLineContent);
        listTextRecord = new TextExpressionRecord(listText);
        listTextRecord.parseRecordAndSubRecords();
    }

    public String getMethodName() {
        return methodName;
    }

    public TextExpressionRecord getListTextRecord() {
        return listTextRecord;
    }

    public String getAttach() {
        return attach;
    }
}
