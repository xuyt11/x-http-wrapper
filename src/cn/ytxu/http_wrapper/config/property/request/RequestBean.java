package cn.ytxu.http_wrapper.config.property.request;

import cn.ytxu.http_wrapper.config.property.request.optional_request_method.OptionalRequestMethodBean;

/**
 * Created by Administrator on 2016/9/5.
 */
public class RequestBean {
    private RESTfulBean RESTful;
    private OptionalRequestMethodBean optional_request_method = OptionalRequestMethodBean.DEFAULT;

    public RESTfulBean getRESTful() {
        return RESTful;
    }

    public OptionalRequestMethodBean getOptional_request_method() {
        return optional_request_method;
    }

}
