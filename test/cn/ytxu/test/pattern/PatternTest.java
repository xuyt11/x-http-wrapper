package cn.ytxu.test.pattern;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.template.expression.util.PatternHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/28.
 */
public class PatternTest {

    private static final String textStart = "<t:list_attach";
    private static final String textEach = "( each=\"\\w+\")";
    private static final String textAttach = "( attach=\"\\w+\")";
    private static final String textListStart = "( text_start=\"[\\p{Print}\\p{Space}]+\")?";
    private static final String textListText = "( list_temp=\"[\\p{Print}\\p{Space}]+\")";
    private static final String textListEnd = "( text_end=\"[\\p{Print}\\p{Space}]+\")?";
    private static final String textEnd = "/>";
    private static final String text = textStart + textEach + textAttach + textListStart + textListText + textListEnd + textEnd;
    private static final Pattern textPattern = Pattern.compile(text);
    private static final String realText =
            "\t <t:list_attach" +
                    " each=\"RESTful_fields\"" +
                    " attach=\"restful_name_list\"" +
                    " text_start=\"1\"" +
                    " list_temp=\"${RESTful_field_name}: String, \"" +
                    " text_end=\"2\"" +
                    "/>\n";

    @Test
    public void testListAttachParser() {
//        LogUtil.i(realText);
//        ListAttachAttrParser parser = new ListAttachAttrParser(textPattern, realText);
//        parser.parse();
//
//        Assert.assertEquals(parser.getMethodName(), "RESTful_fields");
//        Assert.assertEquals(parser.getAttach(), "restful_name_list");
//        Assert.assertEquals(parser.getTextRecord().getStartLineContent(), "${RESTful_field_name}: String, ");

    }

    @Test
    public void testGroup() {
        Matcher matcher = textPattern.matcher(realText);
        int findCount = 0;
        while (matcher.find()) {
            findCount++;
            System.out.println("Group 0:" + matcher.group(0));//得到第0组——整个匹配
            System.out.println("Group 1:" + matcher.group(1));//得到第一组匹配——与(or)匹配的
            System.out.println("Group 2:" + matcher.group(2));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
            System.out.println("Group 3:" + matcher.group(3));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
            System.out.println("Group 4:" + matcher.group(4));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
            System.out.println("Group 5:" + matcher.group(5));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
            System.out.println("Start 0:" + matcher.start(0) + " End 0:" + matcher.end(0));//总匹配的索引
            System.out.println("Start 1:" + matcher.start(1) + " End 1:" + matcher.end(1));//第一组匹配的索引
            System.out.println("Start 2:" + matcher.start(2) + " End 2:" + matcher.end(2));//第二组匹配的索引
            System.out.println("Start 3:" + matcher.start(3) + " End 2:" + matcher.end(3));//第二组匹配的索引
            System.out.println("Start 4:" + matcher.start(4) + " End 2:" + matcher.end(4));//第二组匹配的索引
            System.out.println("Start 5:" + matcher.start(5) + " End 2:" + matcher.end(5));//第二组匹配的索引
            System.out.println(realText.substring(matcher.start(0), matcher.end(1)));//从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
        }

        Assert.assertNotEquals(findCount, 0);

    }

    @Test
    public void testFull() {
        PatternHelper.PatternModel model = new PatternHelper.PatternModel(" list_text=\"", "\"", textPattern);

        boolean isMatch = PatternHelper.matchThisPattern(model, realText);
        Assert.assertEquals(isMatch, true);
    }

    @Test
    public void testEach() {
        Matcher m = textPattern.matcher(realText);
        m.find();
        LogUtil.i(m.group(1));

        Assert.assertFalse(m.start(1) == -1);
        if (m.start(1) == -1) {// not find this group
            return;
        }

        String group = m.group(1);
        String content = group.substring(" each=\"".length(), group.length() - "\"".length());
        Assert.assertEquals(content, "RESTful_fields");
    }

    @Test
    public void testAttach() {
        PatternHelper.PatternModel model = new PatternHelper.PatternModel(" attach=\"", "\"",
                Pattern.compile(textAttach));

        boolean isMatch = PatternHelper.matchThisPattern(model, realText);
        Assert.assertEquals(isMatch, true);

        String content = PatternHelper.getPatternValue(model, realText);
        Assert.assertEquals(content, "restful_name_list");
    }

    @Test
    public void testListStart() {
        PatternHelper.PatternModel model = new PatternHelper.PatternModel(" text_start=\"", "\"",
                Pattern.compile(text));

        boolean isMatch = PatternHelper.matchThisPattern(model, realText);
        Assert.assertEquals(isMatch, false);

//        String content = PatternHelper.getPatternValue(model, realText);
//        Assert.assertEquals(content, "1");

    }

    @Test
    public void testListEnd() {
        PatternHelper.PatternModel model = new PatternHelper.PatternModel("list_end=\"", "\"",
                Pattern.compile(text));

        boolean isMatch = PatternHelper.matchThisPattern(model, realText);
        Assert.assertEquals(isMatch, false);

//        String content = PatternHelper.getPatternValue(model, realText);
//        Assert.assertEquals(content, "2");
    }

    @Test
    public void testListText() {
        PatternHelper.PatternModel model = new PatternHelper.PatternModel(" list_text=\"", "\"",
                Pattern.compile(textListText));

        boolean isMatch = PatternHelper.matchThisPattern(model, realText);
        Assert.assertEquals(isMatch, true);

        String content = PatternHelper.getPatternValue(model, realText);
        Assert.assertEquals(content, "${RESTful_field_name}: String, ");
    }
}
