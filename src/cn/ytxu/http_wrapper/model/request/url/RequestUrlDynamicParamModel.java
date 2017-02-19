package cn.ytxu.http_wrapper.model.request.url;

import cn.ytxu.http_wrapper.model.BaseModel;

import java.text.DecimalFormat;

/**
 * Created by ytxu on 2016/8/14.
 * URL上动态输入的参数model
 * 若有动态输入参数，则需要靠其拼凑出真正请求的URL
 * 动态参数在URL上的格式：{id}, {recommend_id}, {YYYY-MM-DD}...
 */
public class RequestUrlDynamicParamModel extends BaseModel<RequestUrlModel> {

    private final String param;// 在url或multiUrl中的字符串
    private final String realParam;// 在代码中实际的字符串
    private final int paramIndex;// 在url或multiUrl中所有param的index
    private final int start, end;// param 在url或multiUrl中的范围(range), 在转换请求url时，替换的范围

    public RequestUrlDynamicParamModel(RequestUrlModel higherLevel, String param, String realParam, int paramIndex, int start, int end) {
        super(higherLevel);
        this.param = param;
        this.realParam = realParam;
        this.paramIndex = paramIndex;
        this.start = start;
        this.end = end;
        higherLevel.addDynamicParam(this);
    }

    public String getParam() {
        return param;
    }

    public String getRealParam() {
        return realParam;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }


    //*************** reflect method area ***************
    public String url_path() {
        return realParam;
    }

    public String url_path_dynamic_param() {
        int indexOfParams = paramIndex;// getHigherLevel().getDynamicParams().indexOf(this);
        String formatIndex = new DecimalFormat("00").format(indexOfParams);
        return url_path() + formatIndex;
    }

}
