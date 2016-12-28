package cn.ytxu.http_wrapper.template.expression.record.list_attach;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.record.retain.RetainModel;

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
 */
public class ListAttachExpressionRecord extends ExpressionRecord {
    // TODO 添加text_start, text_end attrs and need test pattern
    public static final Pattern[] PATTERNS = {Pattern.compile("(<t:list_attach each=\")\\w+(\" attach=\")\\w+(\" list_text=\")[\\p{Print}\\p{Space}]+(\"/>)")};

    private ListAttachParser parser;

    public ListAttachExpressionRecord(String startLineContent) {
        super(ExpressionEnum.list_attach, startLineContent, true);
    }


    //********************** parse content to record **********************
    @Override
    protected boolean convertContentsIfHas(ListIterator<String> contentListIterator) {
        // do nothing...
        return true;
    }

    @Override
    public boolean isEndTagLine(String content) {
        throw new IllegalAccessError("list attach type not has end tag");
    }


    //********************** loop parse record **********************
    @Override
    public void parseRecordAndSubRecords() {
        parser = new ListAttachParser(startLineContent);
        parser.parse();
    }


    @Override
    public StringBuffer getWriteBuffer(BaseModel reflectModel, RetainModel retain) {
        ListAttachCreater creater = new ListAttachCreater(parser.getMethodName(), parser.getTextRecord());
        String attachContent = creater.getAttachContent(reflectModel, retain);
        reflectModel.attach(parser.getAttach(), attachContent);
        return new StringBuffer();
    }
}
