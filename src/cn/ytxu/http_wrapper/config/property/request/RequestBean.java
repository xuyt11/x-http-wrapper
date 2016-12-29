package cn.ytxu.http_wrapper.config.property.request;

import cn.ytxu.http_wrapper.config.property.request.optional_request_method.OptionalRequestMethodBean;

/**
 * Created by Administrator on 2016/9/5.
 */
public class RequestBean {
    private UrlParamBean url_param;
    private OptionalRequestMethodBean optional_request_method = OptionalRequestMethodBean.DEFAULT;

    public UrlParamBean getUrlParam() {
        return url_param;
    }

    public OptionalRequestMethodBean getOptional_request_method() {
        return optional_request_method;
    }

}
