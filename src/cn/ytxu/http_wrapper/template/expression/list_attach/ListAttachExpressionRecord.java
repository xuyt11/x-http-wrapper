package cn.ytxu.http_wrapper.template.expression.list_attach;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;

import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/12/27.
 * 帖附在对应的反射对象上，让
 * 替换遍历表达式的记录
 * tip: 内部子记录都是text expression record
 */
public class ListAttachExpressionRecord extends ExpressionRecord {
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
    protected boolean isEndTagLine(String content) {
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
        ListAttachCreater creater = new ListAttachCreater(parser.getMethodName(), parser.getListTextRecord());
        String attachContent = creater.getAttachContent(reflectModel, retain);
// TODO
        //        reflectModel.attach(parser.getAttach(), attachContent);
        return new StringBuffer();
    }
}
