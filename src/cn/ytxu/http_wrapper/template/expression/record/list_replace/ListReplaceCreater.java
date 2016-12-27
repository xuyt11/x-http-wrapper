package cn.ytxu.http_wrapper.template.expression.record.list_replace;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.record.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template.expression.util.ReflectiveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/14.
 * ListReplaceExpressionRecord的代码生成器
 */
public class ListReplaceCreater {
    private String methodName;
    private TextExpressionRecord listValueRecord;
    private String replaceContent;
    private List<String> contents;

    public ListReplaceCreater(String methodName, TextExpressionRecord listValueRecord, String replaceContent, List<String> contents) {
        this.methodName = methodName;
        this.listValueRecord = listValueRecord;
        this.replaceContent = replaceContent;
        this.contents = contents;
    }

    public StringBuffer getWriteBuffer(BaseModel reflectModel, RetainModel retain) {
        String listValue;
        List subModels = ReflectiveUtil.getList(reflectModel, methodName);
        if (subModels == null || subModels.isEmpty()) {
            listValue = "";
        } else {
            // 需要在解析完成listValue之后，才能生成subs，因为需要替换replaceContent
            listValue = parseAndGetListValue(retain, subModels);
        }

        List<String> newContents = getNewContents(listValue);
        List<ExpressionRecord> subs = createSubs(newContents);

        return getListReplaceBufferBySubs(reflectModel, retain, subs);
    }

    private String parseAndGetListValue(RetainModel retain, List<BaseModel> subModels) {
        StringBuffer listValueBuffer = new StringBuffer();
        TextExpressionRecord record = listValueRecord;
        for (BaseModel subModel : subModels) {
            listValueBuffer.append(record.getNormalWriteBuffer(subModel, retain));
        }
        return listValueBuffer.toString();
    }

    private List<String> getNewContents(String listValue) {
        List<String> newContents = new ArrayList<>(contents.size());
        for (String content : contents) {
            String newContent;
            if (content.contains(replaceContent)) {
                newContent = content.replace(replaceContent, listValue);
            } else {
                newContent = content;
            }
            newContents.add(newContent);
        }
        return newContents;
    }

    private List<ExpressionRecord> createSubs(List<String> newContents) {
        List<ExpressionRecord> subs = new ArrayList<>(newContents.size());
        for (String content : newContents) {
            TextExpressionRecord tsr = new TextExpressionRecord(content);
            tsr.parseRecordAndSubRecords();
            subs.add(tsr);
        }
        return subs;
    }

    private StringBuffer getListReplaceBufferBySubs(BaseModel reflectModel, RetainModel retain, List<ExpressionRecord> subs) {
        StringBuffer listReplaceBuffer = new StringBuffer();
        for (ExpressionRecord sub : subs) {
            listReplaceBuffer.append(sub.getWriteBuffer(reflectModel, retain));
        }
        return listReplaceBuffer;
    }

}
