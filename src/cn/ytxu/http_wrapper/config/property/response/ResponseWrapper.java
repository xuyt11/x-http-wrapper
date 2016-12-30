package cn.ytxu.http_wrapper.config.property.response;

import cn.ytxu.http_wrapper.common.util.LogUtil;

import java.util.List;
import java.util.Objects;

/**
 * 基础response必须的字段的字段名称
 */
public class ResponseWrapper {

    private static ResponseWrapper instance;

    private ResponseBean response;

    public static ResponseWrapper getInstance() {
        return instance;
    }

    public static void load(ResponseBean response) {
        LogUtil.i(ResponseWrapper.class, "load response property start...");
        instance = new ResponseWrapper(response);
        LogUtil.i(ResponseWrapper.class, "load response property success...");
    }

    private ResponseWrapper(ResponseBean response) {
        this.response = response;

        if (Objects.isNull(response.getStatusCode())) {
            throw new IllegalArgumentException("u must setup response statusCode property...");
        }
        if (Objects.isNull(response.getMessage())) {
            throw new IllegalArgumentException("u must setup response message property...");
        }
        if (Objects.isNull(response.getError())) {
            throw new IllegalArgumentException("u must setup response error property...");
        }
        if (Objects.isNull(response.getData())) {
            throw new IllegalArgumentException("u must setup response data property...");
        }
    }

    public String getStatusCode() {
        return response.getStatusCode();
    }

    public String getMessage() {
        return response.getMessage();
    }

    public String getError() {
        return response.getError();
    }

    public String getErrorType() {
        return response.getErrorType();
    }

    public String getData() {
        return response.getData();
    }

    public List<BaseResponseParamBean> getAll() {
        return response.getAll();
    }
}