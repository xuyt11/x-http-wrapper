package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output;

import cn.ytxu.http_wrapper.config.property.param_type.ParamTypeEnum;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;


/**
 * Created by ytxu on 2016/8/17.
 */
public class OutputParamParser {

    private final ResponseModel response;
    private final JsonObject bodyJObj;

    public OutputParamParser(ResponseModel response) {
        this.response = response;
        this.bodyJObj = new JsonParser().parse(response.getBody()).getAsJsonObject();
    }

    public void start() {
        List<OutputParamModel> outputs = getOutputsOfResponse(bodyJObj);
        // 判断依据是当前是否需要解析outputs,若需要，则需要解析子outputs
        parseValueAndValuesOfOutputsThenParseSubsIfCan(outputs);
        response.setOutputs(outputs);
    }


    //********************** parse output of response **********************
    private List<OutputParamModel> getOutputsOfResponse(JsonObject jsonObject) {
        return getOutputs(jsonObject, null);
    }

    public List<OutputParamModel> getOutputs(JsonObject jsonObject, OutputParamModel parent) {
        if (jsonObject.isJsonNull()) {
            return Collections.EMPTY_LIST;
        }

        List<OutputParamModel> outputs = new ArrayList<>(jsonObject.size());

        Set<Map.Entry<String, JsonElement>> entrySet =  jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String fieldName = entry.getKey();
            JsonElement fieldValue = entry.getValue();
            ParamTypeEnum type = ParamTypeEnum.get(fieldValue);
            OutputParamModel output = type.createOutput(response, parent, fieldName, fieldValue);
            outputs.add(output);
        }
        return outputs;
    }


    //********************** loop parse outputs and its subs **********************
    private void parseValueAndValuesOfOutputsThenParseSubsIfCan(List<OutputParamModel> outputs) {
        List<OutputParamModel> allSubsOfOuputs = parseValueAndValuesOfOutputsAndReturnAllSubsOfOutputs(outputs);
        if (isNeedParseSubs(allSubsOfOuputs)) {
            parseValueAndValuesOfOutputsThenParseSubsIfCan(allSubsOfOuputs);
        }
    }

    private List<OutputParamModel> parseValueAndValuesOfOutputsAndReturnAllSubsOfOutputs(List<OutputParamModel> outputs) {
        List<OutputParamModel> subsOfOutputs = new ArrayList<>();
        for (OutputParamModel output : outputs) {
            List<OutputParamModel> subsOfOutput = parseValueAndValuesOfOutputAndReturnSubsOfOutput(output);
            if (isNeedAdd(subsOfOutput)) {
                subsOfOutputs.addAll(subsOfOutput);
            }
        }
        return subsOfOutputs;
    }

    private List<OutputParamModel> parseValueAndValuesOfOutputAndReturnSubsOfOutput(OutputParamModel output) {
        output.getType().parseValueAndValuesIfCan(this, output);
        return output.getSubs();
    }

    private boolean isNeedAdd(List<OutputParamModel> subsOfOutput) {
        return !subsOfOutput.isEmpty();
    }

    private boolean isNeedParseSubs(List<OutputParamModel> allSubsOfOuputs) {
        return !allSubsOfOuputs.isEmpty();
    }


}
