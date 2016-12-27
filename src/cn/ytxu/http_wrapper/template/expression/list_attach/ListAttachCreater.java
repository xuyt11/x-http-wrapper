package cn.ytxu.http_wrapper.template.expression.list_attach;

import cn.ytxu.http_wrapper.template.expression.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template_engine.parser.util.ReflectiveUtil;

import java.util.List;

/**
 * Created by ytxu on 2016/12/27.
 * ListAttachExpressionRecord的代码生成器
 */
public class ListAttachCreater {
    private String methodName;
    private TextExpressionRecord listTextRecord;

    public ListAttachCreater(String methodName, TextExpressionRecord listTextRecord) {
        this.methodName = methodName;
        this.listTextRecord = listTextRecord;
    }

    public String getAttachContent(Object reflectModel, RetainModel retain) {
        String attachContent;
        List subModels = ReflectiveUtil.getList(reflectModel, methodName);
        if (subModels == null || subModels.isEmpty()) {
            attachContent = "";
        } else {
            // 需要在解析完成listText之后，才能生成subs，
            attachContent = parseAndGetListValue(retain, subModels);
        }
        return attachContent;
    }

    private String parseAndGetListValue(RetainModel retain, List subModels) {
        StringBuffer listTextBuffer = new StringBuffer();
        TextExpressionRecord record = listTextRecord;
        for (Object subModel : subModels) {
            listTextBuffer.append(record.getNormalWriteBuffer(subModel, retain));
        }
        return listTextBuffer.toString();
    }


}
