package cn.ytxu.http_wrapper.config.property.response;

import java.util.Arrays;
import java.util.List;

/**
 * 基础response必须的字段的字段名称；
 * format(key:value-->value:base response entity name)
 */
public class ResponseBean {
    private BaseResponseParamBean statusCode;
    private BaseResponseParamBean message;
    private BaseResponseParamBean error;
    private BaseResponseParamBean data;

    public String getStatusCode() {
        return statusCode.getName();
    }

    public String getMessage() {
        return message.getName();
    }

    public String getError() {
        return error.getName();
    }

    public String getData() {
        return data.getName();
    }

    public List<BaseResponseParamBean> getAll() {
        return Arrays.asList(statusCode, message, error, data);
    }

    public String getErrorType() {
        return error.getType();
    }

}