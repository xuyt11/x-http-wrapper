package cn.ytxu.http_wrapper.template.expression.record.list_attach;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.template.expression.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template.expression.record.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.util.ReflectiveUtil;

import java.util.List;

/**
 * Created by ytxu on 2016/12/27.
 * ListAttachExpressionRecord的代码生成器
 */
public class ListAttachCreater {
    private String methodName;
    private TextExpressionRecord textStartRecord, textTempRecord, textEndRecord;

    public ListAttachCreater(String methodName, TextExpressionRecord textStartRecord,
                             TextExpressionRecord textTempRecord, TextExpressionRecord textEndRecord) {
        this.methodName = methodName;
        this.textStartRecord = textStartRecord;
        this.textTempRecord = textTempRecord;
        this.textEndRecord = textEndRecord;
    }


    public String getAttachContent(BaseModel reflectModel, RetainModel retain) {
        StringBuffer attachContentBuffer = new StringBuffer();
        attachContentBuffer.append(getTextStart(reflectModel, retain));
        attachContentBuffer.append(getTextTemp(reflectModel, retain));
        attachContentBuffer.append(getTextEnd(reflectModel, retain));
        return attachContentBuffer.toString();
    }

    private StringBuffer getTextStart(BaseModel reflectModel, RetainModel retain) {
        return textStartRecord.getNormalWriteBuffer(reflectModel, retain);
    }

    private StringBuffer getTextTemp(BaseModel reflectModel, RetainModel retain) {
        List<BaseModel> subModels = ReflectiveUtil.getList(reflectModel, methodName);
        if (subModels == null || subModels.isEmpty()) {
            return new StringBuffer();
        }
        return parseAndGetTempContent(retain, subModels);
    }

    private StringBuffer parseAndGetTempContent(RetainModel retain, List<BaseModel> subModels) {
        StringBuffer tempBuffer = new StringBuffer();
        for (BaseModel subModel : subModels) {
            tempBuffer.append(textTempRecord.getNormalWriteBuffer(subModel, retain));
        }
        return tempBuffer;
    }

    private StringBuffer getTextEnd(BaseModel reflectModel, RetainModel retain) {
        return textEndRecord.getNormalWriteBuffer(reflectModel, retain);
    }

}
