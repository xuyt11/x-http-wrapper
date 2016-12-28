package cn.ytxu.http_wrapper.template.expression.record.list_attach;

import cn.ytxu.http_wrapper.template.expression.record.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.util.PatternHelper;

import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/12/27.
 */
public class ListAttachParser {

    private String startLineContent;
    private String methodName, attach, textStart, textEnd;
    private TextExpressionRecord textRecord;

    private enum Attr {
        each("需要循环的list",
                new PatternHelper.PatternModel("each=\"", "\"",
                        Pattern.compile("(each=\")\\w+(\")"))),
        attach("需要在contents中替换的字符串",
                new PatternHelper.PatternModel("attach=\"", "\"",
                        Pattern.compile("(attach=\")\\w+(\")"))),
        text_start("数据填充后，插入到首位",
                new PatternHelper.PatternModel("text_start=\"", "\"",
                        Pattern.compile("(text_start=\")[\\p{Print}\\p{Space}]+(\")"))),
        list_text("遍历的数据模板",
                new PatternHelper.PatternModel("list_text=\"", "\"",
                        Pattern.compile("(list_text=\")[\\p{Print}\\p{Space}]+(\")"))),
        text_end("数据填充后，添加到末尾",
                new PatternHelper.PatternModel("text_end=\"", "\"",
                        Pattern.compile("(text_end=\")[\\p{Print}\\p{Space}]+(\")")));

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
        textStart = getText(Attr.text_start.patternModel);
        attach = PatternHelper.getPatternValue(Attr.attach.patternModel, startLineContent);
        textEnd = getText(Attr.text_end.patternModel);

        String listText = PatternHelper.getPatternValue(Attr.list_text.patternModel, startLineContent);
        textRecord = new TextExpressionRecord(textStart + listText + textEnd);
        textRecord.parseRecordAndSubRecords();
    }

    private String getText(PatternHelper.PatternModel model) {
        boolean isMatch = PatternHelper.matchThisPattern(model, startLineContent);
        if (isMatch) {
            return PatternHelper.getPatternValue(model, startLineContent);
        }
        return "";
    }

    public String getMethodName() {
        return methodName;
    }

    public TextExpressionRecord getTextRecord() {
        return textRecord;
    }

    public String getAttach() {
        return attach;
    }
}
