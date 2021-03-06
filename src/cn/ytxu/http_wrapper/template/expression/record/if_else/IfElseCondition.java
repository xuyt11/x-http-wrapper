package cn.ytxu.http_wrapper.template.expression.record.if_else;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.template.expression.util.ReflectiveUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * if else 表达式的判断类型枚举
 */
public enum IfElseCondition {
    /**
     * boolean类型判断
     */
    Boolean("isTrue=\"", "\"", Pattern.compile("(isTrue=\")\\w+(\")")) {
        @Override
        public boolean getBoolean(BaseModel reflectModel, String methodName) {
            return ReflectiveUtil.getBoolean(reflectModel, methodName);
        }
    },
    /**
     * String类型判断
     */
    NotEmpty("isNotEmpty=\"", "\"", Pattern.compile("(isNotEmpty=\")\\w+(\")")) {
        @Override
        public boolean getBoolean(BaseModel reflectModel, String methodName) {
            String text = ReflectiveUtil.getString(reflectModel, methodName);
            if (text.isEmpty()) {
                return false;
            }
            return true;
        }
    },
    /**
     * String类型判断：字符串是否一样，不需要大小写判断
     */
    ListSize("ListSize=\"", "\"", Pattern.compile("(ListSize=\")\\w+(,)\\w+(\")")) {
        @Override
        public boolean getBoolean(BaseModel reflectModel, String methodName) {
            String[] datas = methodName.split(",");
            String realMethodName = datas[0];
            int expectedSize = Integer.parseInt(datas[1]);
            List list = ReflectiveUtil.getList(reflectModel, realMethodName);
            int actualSize = Objects.isNull(list) ? 0 : list.size();
            return actualSize == expectedSize;
        }
    },
    /**
     * String类型判断：字符串是否一样
     */
    Equals("Equals=\"", "\"", Pattern.compile("(Equals=\")\\w+(,)\\w+(\")")) {
        @Override
        public boolean getBoolean(BaseModel reflectModel, String methodName) {
            String[] datas = methodName.split(",");
            String realMethodName = datas[0];
            String value = datas[1];
            String text = ReflectiveUtil.getString(reflectModel, realMethodName);
            return text.equals(value);
        }
    },
    /**
     * String类型判断：字符串是否一样，不需要大小写判断
     */
    EqualsIgnoreCase("EqualsIgnoreCase=\"", "\"", Pattern.compile("(EqualsIgnoreCase=\")\\w+(,)\\w+(\")")) {
        @Override
        public boolean getBoolean(BaseModel reflectModel, String methodName) {
            String[] datas = methodName.split(",");
            String realMethodName = datas[0];
            String value = datas[1];
            String text = ReflectiveUtil.getString(reflectModel, realMethodName);
            return text.equalsIgnoreCase(value);
        }
    };
//        String("字符串类型判断");


    private final String pattern_front;
    private final String pattern_end;
    private final Pattern pattern;

    IfElseCondition(String pattern_front, String pattern_end, Pattern pattern) {
        this.pattern_front = pattern_front;
        this.pattern_end = pattern_end;
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getMethodName(String startTagContent) {
        Matcher matcher = pattern.matcher(startTagContent);
        // be sure to match, but also need call matcher.find()
        matcher.find();
        String group = matcher.group();
        int methodNameStart = pattern_front.length();
        int methodNameEnd = group.length() - pattern_end.length();
        String methodName = group.substring(methodNameStart, methodNameEnd);
        return methodName;
    }

    public static IfElseCondition get(String startTagContent) {
        for (IfElseCondition condition : IfElseCondition.values()) {
            if (condition.isThisConditionType(startTagContent)) {
                return condition;
            }
        }
        throw new IllegalStateException("can not find condition, and this content is " + startTagContent);
    }

    /**
     * 是否为该判断类型
     */
    private boolean isThisConditionType(String startTagContent) {
        Matcher matcher = pattern.matcher(startTagContent);
        return matcher.find();
    }

    public abstract boolean getBoolean(BaseModel reflectModel, String methodName);

    public static Pattern[] getPatterns() {
        return Store.PATTERNS;
    }

}

class Store {
    /**
     * if else condition all pattern
     */
    static final Pattern[] PATTERNS;

    static {
        IfElseCondition[] conditions = IfElseCondition.values();
        PATTERNS = new Pattern[conditions.length];
        for (int i = 0; i < conditions.length; i++) {
            PATTERNS[i] = conditions[i].getPattern();
        }
    }

}