package cn.ytxu.http_wrapper.config.property.param_type;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.ArrayTypeOutputParser;
import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.ObjectTypeOutputParser;
import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.OutputParamParser;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 16/12/16.
 * 参数的类型：包含请求与响应参数
 * tip: 顺序是不能改动的，最后一个是确保为未知类型，其他：如long在interger之后，float在double之后
 */
public enum ParamTypeEnum {
    INTEGER(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    /**
     * FUTURE 未来将会删除掉的类型，这样的类型，不能知道精确类型
     */
    @Deprecated
    NUMBER(Number.class),

    BOOLEAN(Boolean.class),

    STRING(String.class),

    OBJECT(JsonObject.class) {
        @Override
        public void parseValueAndValuesIfCan(OutputParamParser parser, OutputParamModel output) {
            new ObjectTypeOutputParser(parser, output).start();
        }

        @Override
        public void replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            addValueIfNeed(target, model);
        }

        @Override
        public String getResponseParamType(String response_param_type, OutputParamModel output) {
            return output.entity_class_name();
        }
    },

    /**
     * tip: JSONArray中不能包含JSONArray，这种的数据结构，我不解析
     */
    ARRAY(JsonArray.class) {
        @Override
        public void parseValueAndValuesIfCan(OutputParamParser parser, OutputParamModel output) {
            new ArrayTypeOutputParser(parser, output).start();
        }

        @Override
        public void replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing
                (List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            addValueIfNeed(target, model);
        }

        @Override
        public String getResponseParamType(String response_param_type, OutputParamModel output) {
            String etContent = super.getResponseParamType(response_param_type, output);
            ParamTypeEnum subElementType = output.getSubType();
            String subElementTypeStr = getSubTypeContent(response_param_type, output, subElementType);
            etContent = etContent.replace("${object}", subElementTypeStr);
            return etContent;
        }

        private String getSubTypeContent(String response_param_type, OutputParamModel output, ParamTypeEnum subElementType) {
            String subElementTypeStr;
            if (subElementType == OBJECT) {
                subElementTypeStr = OBJECT.getResponseParamType(response_param_type, output);
            } else {
                subElementTypeStr = ConfigWrapper.getParamType().getParamTypeBean(subElementType).getRequestOptionalParamType();
            }
            return subElementTypeStr;
        }
    },

    MAP(JsonObject.class) {
        @Override
        public String getResponseParamType(String response_param_type, OutputParamModel output) {
            String etContent = super.getResponseParamType(response_param_type, output);
            ParamTypeEnum subElementType = output.getSubType();
            String subElementTypeStr = getSubTypeContent(response_param_type, output, subElementType);
            etContent = etContent.replace("${object}", subElementTypeStr);
            return etContent;
        }

        private String getSubTypeContent(String response_param_type, OutputParamModel output, ParamTypeEnum subElementType) {
            String subElementTypeStr;
            if (subElementType == OBJECT) {
                subElementTypeStr = OBJECT.getResponseParamType(response_param_type, output);
            } else {
                subElementTypeStr = ConfigWrapper.getParamType().getParamTypeBean(subElementType).getRequestOptionalParamType();
            }
            return subElementTypeStr;
        }
    },

    DATE(String.class) {
        @Override
        public String getResponseParamType(String response_param_type, OutputParamModel output) {
            throw new RuntimeException("output param must not be date type");
        }
    },
    FILE(String.class) {
        @Override
        public String getResponseParamType(String response_param_type, OutputParamModel output) {
            throw new RuntimeException("output param must not be file type");
        }
    },
    UNKNOWN(null) {
        @Override
        public void replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing
                (List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            int index = outputs.indexOf(target);
            outputs.set(index, model);
        }
    };


    private final Class clazz;

    ParamTypeEnum(Class clazz) {
        this.clazz = clazz;
    }


    public static ParamTypeEnum get(JsonElement fieldValue) {
        if (Objects.isNull(fieldValue) || fieldValue.isJsonNull()) {
            return UNKNOWN;
        }
        if (fieldValue.isJsonArray()) {
            return ARRAY;
        }
        if (fieldValue.isJsonObject()) {
            return OBJECT;
        }
        if (fieldValue.isJsonPrimitive()) {
            JsonPrimitive value = (JsonPrimitive) fieldValue;
            if (value.isBoolean()) {
                return BOOLEAN;
            }
            if (value.isString()) {
                return STRING;
            }
            if (value.isNumber()) {
                return getNumberType(value);
            }
        }

        return UNKNOWN;
    }

    private static ParamTypeEnum getNumberType(JsonPrimitive value) {
        String valueText = value.getAsString();
        try {
            Integer.parseInt(valueText);
            return INTEGER;
        } catch (NumberFormatException ignore) {
        }
        try {
            Long.parseLong(valueText);
            return LONG;
        } catch (NumberFormatException ignore) {
        }
        try {
            Float.parseFloat(valueText);
            return FLOAT;
        } catch (NumberFormatException ignore) {
        }
        try {
            Double.parseDouble(valueText);
            return DOUBLE;
        } catch (NumberFormatException ignore) {
        }
        return NUMBER;
    }

    public OutputParamModel createOutput(ResponseModel response, OutputParamModel parent, String fieldName, JsonElement fieldValue) {
        return new OutputParamModel(response, parent, this, fieldName, fieldValue);
    }

    /**
     * @return output中的subs
     */
    public void parseValueAndValuesIfCan(OutputParamParser parser, OutputParamModel output) {
        return;
    }

    /**
     * tips:
     * if taget type is NULL, replace it with model;
     * else if target Type is Object or Array, add model`s value to target`s values
     * otherwise, do nothing...
     */
    public void replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
    }

    protected void addValueIfNeed(OutputParamModel target, OutputParamModel model) {
        JsonElement value = model.getValue();
        ParamTypeEnum type = ParamTypeEnum.get(value);
        if (type == UNKNOWN) {// model`s type is NULL, so do nothing...
            return;
        }
        if (type != this) {// model`s type is not same for target`s type
            throw new IllegalStateException("the value type is not match" +
                    "\nin request " + model.getHigherLevel().getHigherLevel().getHigherLevel().getName() +
                    "\n and the output name is " + model.getName() + ", its previous type is " + this.name() + ", but curr type is " + type.name());
        }
        target.addValue(value);
        return;
    }

    public String getResponseParamType(String response_param_type, OutputParamModel output) {
        return response_param_type;
    }
}
