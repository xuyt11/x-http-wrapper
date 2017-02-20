package cn.ytxu.http_wrapper.template.expression.record.list_attach;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.sub.ListAttachSubExpression;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.sub.ListAttachSubRecordEntity;
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
    private ListAttachSubRecordEntity subRecordEntity;

    public ListAttachCreater(String methodName, ListAttachSubRecordEntity subRecordEntity) {
        this.methodName = methodName;
        this.subRecordEntity = subRecordEntity;
    }


    public String getAttachContent(BaseModel reflectModel, RetainModel retain) {
        StringBuffer attachContentBuffer = new StringBuffer();
        attachContentBuffer.append(getTextStart(reflectModel, retain));
        attachContentBuffer.append(getTextTemp(reflectModel, retain));
        attachContentBuffer.append(getTextEnd(reflectModel, retain));
        return attachContentBuffer.toString();
    }

    private StringBuffer getTextStart(BaseModel reflectModel, RetainModel retain) {
        return ListAttachSubExpression.text_start.getThisExpressionRecord(subRecordEntity).getNormalWriteBuffer(reflectModel, retain);
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
        for (int i = 0, count = subModels.size(); i < count; i++) {
            TextExpressionRecord listTempRecord = getListTempRecord(i, count);
            BaseModel subModel = subModels.get(i);
            tempBuffer.append(listTempRecord.getNormalWriteBuffer(subModel, retain));
        }
        return tempBuffer;
    }

    private TextExpressionRecord getListTempRecord(int i, int count) {
        if (i == 0) {// start
            if (ListAttachSubExpression.list_temp_start.hasThisExpression(subRecordEntity)) {
                return ListAttachSubExpression.list_temp_start.getThisExpressionRecord(subRecordEntity);
            }
        } else if (i + 1 == count) {// end
            if (ListAttachSubExpression.list_temp_end.hasThisExpression(subRecordEntity)) {
                return ListAttachSubExpression.list_temp_end.getThisExpressionRecord(subRecordEntity);
            }
        }
        return ListAttachSubExpression.list_temp.getThisExpressionRecord(subRecordEntity);
    }

    private StringBuffer getTextEnd(BaseModel reflectModel, RetainModel retain) {
        return ListAttachSubExpression.text_end.getThisExpressionRecord(subRecordEntity).getNormalWriteBuffer(reflectModel, retain);
    }

}
