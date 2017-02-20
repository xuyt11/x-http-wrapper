package cn.ytxu.http_wrapper.template.expression.record.list_attach;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.attr.ListAttachAttr;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.attr.ListAttachAttrParser;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.sub.ListAttachSubExpression;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.sub.ListAttachSubParser;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.sub.ListAttachSubRecordEntity;
import cn.ytxu.http_wrapper.template.expression.record.retain.RetainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/12/27.
 * 反射获取String数据，并帖附在对应的BaseModel上；
 * 代码：reflectModel.attach(attach, attachContent)
 * <p>
 * tip:
 * 1、在没有找到反射的方法后，查找该BaseModel是否有该attach==methodName的数据，有则返回attachContent
 * 2、不过现在只针对于反射String
 * 3、必须要有list_temp子表达式
 * <p>
 * template:
 * <t:list_attach each="url_dynamic_path" attach="url_dynamic_param_name_format"/>
 * <t:list_attach text_start="   "/>
 * <t:list_attach list_temp="${url_dynamic_param_field_name}: String,"/>
 * <t:list_attach text_end=""/>
 * </t:list_attach>
 * <p>
 */
public class ListAttachExpressionRecord extends ExpressionRecord {

    // <t:list_attach( each="\w+")\"( attach=\"\\w+\")\"/>
    public static final Pattern PATTERN = Pattern.compile(ListAttachAttr.getAttachPatternText());

    private static final String END_TAG = "</t:list_attach>";// 结束标签

    private ListAttachAttrParser attrParser;
    private ListAttachSubRecordEntity subRecordEntity;

    private final List<String> subContents = new ArrayList<>(ListAttachSubExpression.values().length);

    public ListAttachExpressionRecord(String startLineContent) {
        super(ExpressionEnum.list_attach, startLineContent, END_TAG, true);
    }


    //********************** parse content to record **********************
    @Override
    protected boolean convertContentsIfHas(ListIterator<String> contentListIterator) {
        while (contentListIterator.hasNext()) {
            String content = contentListIterator.next();
            if (isEndTagLine(content)) {
                return true;
            }
            subContents.add(content);
        }
        return false;
    }


    //********************** loop parse record **********************
    @Override
    public void parseRecordAndSubRecords() {
        attrParser = new ListAttachAttrParser(PATTERN, startLineContent).parse();
        subRecordEntity = new ListAttachSubParser(subContents).parse();
    }


    @Override
    public StringBuffer getWriteBuffer(BaseModel reflectModel, RetainModel retain) {
        ListAttachCreater creater = new ListAttachCreater(attrParser.getMethodName(), subRecordEntity);
        String attachContent = creater.getAttachContent(reflectModel, retain);
        reflectModel.attach(attrParser.getAttach(), attachContent);
        return new StringBuffer();
    }
}
