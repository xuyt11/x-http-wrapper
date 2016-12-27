package cn.ytxu.http_wrapper.template.engine.converter;

import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;

import java.util.List;

/**
 * callback of parse content to expression record converter
 * tip: 若是有中间标签(middleTag)的表达式，callback不能为null
 */
public interface C2ERCallback {
    /**
     * 碰到了表达式中间的那些标签
     *
     * @param content 中间标签的content，没有trim，没有做任何操作的数据
     * @param records 该中间标签之上的所有的记录
     */
    void middleTagLine(String content, List<ExpressionRecord> records);

    /**
     * 解析到了结束标签
     *
     * @param records 解析到的所有记录
     */
    void endTagLine(List<ExpressionRecord> records);
}
