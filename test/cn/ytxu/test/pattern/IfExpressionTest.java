package cn.ytxu.test.pattern;

import cn.ytxu.http_wrapper.template.expression.record.if_else.IfElseCondition;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ytxu on 2017/2/19.
 */
public class IfExpressionTest {

    private static final String startLineContent = "<t:if Equals=\"url_dynamic_path,1231\">";


    @Test
    public void testListAttachAttrParser() {
        IfElseCondition ifElseCondition = IfElseCondition.get(startLineContent);
        Assert.assertEquals(ifElseCondition.name(), "Equals");
        String methodName = ifElseCondition.getMethodName(startLineContent);
        Assert.assertEquals(methodName, "url_dynamic_path,1231");
    }

}
