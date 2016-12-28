package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub.SubOutputParser;
import cn.ytxu.http_wrapper.config.property.param_type.ParamTypeEnum;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by ytxu on 2016/8/21.
 * 数组类型的输出参数的解析器
 * tip: JSONArray中不能包含JSONArray，这种的数据结构，我不解析
 */
public class ArrayTypeOutputParser {

    private final OutputParamParser parser;
    private final OutputParamModel output;

    public ArrayTypeOutputParser(OutputParamParser parser, OutputParamModel output) {
        this.parser = parser;
        this.output = output;
    }

    public void start() {
        setSubType();

        if (needParseValueAndValuesIfSubTypeIsJsonObjectType()) {
            parseValue();
            parseValues();
        }
    }


    //******************** set sub type ********************
    private void setSubType() {
        if (setSubTypeByValueIfCan()) {
            return;
        }
        if (setSubTypeByValuesIfCan()) {
            return;
        }
        output.setSubType(ParamTypeEnum.UNKNOWN);
    }

    private boolean setSubTypeByValueIfCan() {
        JsonArray jArr = (JsonArray) output.getValue();
        return setSubTypeIfCan(jArr);
    }

    private boolean setSubTypeByValuesIfCan() {
        List<Object> values = output.getValues();
        for (Object value : values) {
            JsonArray jArr = (JsonArray) value;
            if (setSubTypeIfCan(jArr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return has set sub type, and the value is equals can set sub type
     */
    private boolean setSubTypeIfCan(JsonArray jArr) {
        boolean canSetSubType = canSetSubType(jArr);
        if (canSetSubType) {
            ParamTypeEnum subType = ParamTypeEnum.get(jArr.get(0));
            output.setSubType(subType);
        }
        return canSetSubType;
    }

    private boolean canSetSubType(JsonArray jArr) {
        return jArr.size() > 0;
    }


    //******************** parse value ********************
    private boolean needParseValueAndValuesIfSubTypeIsJsonObjectType() {
        return ParamTypeEnum.OBJECT == output.getSubType();
    }

    private void parseValue() {
        parseJSONArray((JsonArray) output.getValue());
    }

    private void parseValues() {
        List<Object> values = output.getValues();
        for (Object value : values) {
            parseJSONArray((JsonArray) value);
        }
    }

    private void parseJSONArray(JsonArray value) {
        for (int i = 0, size = value.size(); i < size; i++) {
            JsonObject subOfValue;
            try {
                subOfValue = value.get(i).getAsJsonObject();
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new ClassCastException(e.getMessage() + "\n" + value.toString());
            }
            new SubOutputParser(parser, output, subOfValue).parse();
        }
    }

}
