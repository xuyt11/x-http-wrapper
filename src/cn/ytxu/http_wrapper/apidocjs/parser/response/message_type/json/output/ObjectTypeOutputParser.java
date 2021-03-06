package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub.SubOutputParser;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by ytxu on 2016/8/20.
 * 对象类型的输出参数的解析器
 */
public class ObjectTypeOutputParser {

    private OutputParamParser parser;
    private OutputParamModel output;

    public ObjectTypeOutputParser(OutputParamParser parser, OutputParamModel output) {
        this.parser = parser;
        this.output = output;
    }

    public void start() {
        parseValue();
        parseValues();
    }

    private void parseValue() {
        parseJSONObject(output.getValue().getAsJsonObject());
    }

    private void parseValues() {
        List<JsonElement> values = output.getValues();
        for (JsonElement value : values) {
            parseJSONObject(value.getAsJsonObject());
        }
    }

    private void parseJSONObject(JsonObject value) {
        new SubOutputParser(parser, output, value).parse();
    }

}
