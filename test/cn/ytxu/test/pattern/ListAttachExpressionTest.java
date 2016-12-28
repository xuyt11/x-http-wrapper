package cn.ytxu.test.pattern;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.ListAttachExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.record.list_attach.attr.ListAttachAttrParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/28.
 */
public class ListAttachExpressionTest {

    String startLineContent;

    String[] subContents, subContents2, subContents3;

    @Before
    public void init() {
        startLineContent = "<t:list_attach each=\"RESTful_fields\" attach=\"restful_name_list\"/>";
        subContents = new String[]{"<t:list_attach list_temp=\"${RESTful_field_name}: String, \"/>"};

        subContents2 = new String[]{"<t:list_attach text_start=\"   \"/>",
                "<t:list_attach list_temp=\"${RESTful_field_name}: String,\"/>"};

        subContents3 = new String[]{"<t:list_attach text_start=\"   \"/>",
                "<t:list_attach list_temp=\"${RESTful_field_name}: String,\"/>",
                "<t:list_attach text_end=\"end line\"/>"};
    }

    @Test
    public void testListAttachAttrParser() {
        ListAttachAttrParser attrParser = new ListAttachAttrParser(ListAttachExpressionRecord.PATTERN, startLineContent).parse();
        Assert.assertEquals(attrParser.getMethodName(), "RESTful_fields");
        Assert.assertEquals(attrParser.getAttach(), "restful_name_list");
    }

//
//    @Test
//    public void testListAttachSubParser() {
//        ListAttachSubParser subParser = new ListAttachSubParser(Arrays.asList(subContents)).parse();
//        Assert.assertEquals(subParser.getTextRecord().getStartLineContent(), "${RESTful_field_name}: String, ");
//    }
//
//    @Test
//    public void testListAttachSubParser2() {
//        ListAttachSubParser subParser = new ListAttachSubParser(Arrays.asList(subContents2)).parse();
//        Assert.assertEquals(subParser.getTextRecord().getStartLineContent(), "   ${RESTful_field_name}: String,");
//    }
//
//    @Test
//    public void testListAttachSubParser3() {
//        ListAttachSubParser subParser = new ListAttachSubParser(Arrays.asList(subContents3)).parse();
//        Assert.assertEquals(subParser.getTextRecord().getStartLineContent(), "   ${RESTful_field_name}: String,end line");
//    }

    @Test
    public void testListAttachParser4() {
        String line = "            <t:list_attach each=\"RESTful_fields\" attach=\"restful_name_list\">";
        Pattern[] patterns = {ListAttachExpressionRecord.PATTERN};
        for (Pattern pattern : patterns) {
            LogUtil.i("" + pattern.matcher(line).find());
        }

    }

}
