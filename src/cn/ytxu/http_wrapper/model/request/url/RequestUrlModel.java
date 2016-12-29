package cn.ytxu.http_wrapper.model.request.url;

import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.request.RequestModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class RequestUrlModel extends BaseModel<RequestModel> {
    /**
     * request url:absolute url
     * 相对路径
     * format：/api/account/verify/
     */
    private final String url;
    /**
     * 是否有需要动态输入的参数，来拼凑出真正请求的URL
     * 动态参数在URL上的格式：{id}, {recommend_id}, {YYYY-MM-DD}...
     */
    private boolean hasDynamicParam = false;
    private boolean hasMultiParam;// 是否有多选类型的参数，若有的话，则使用url时需要使用multiUrl
    private String multiUrl;// url经转换变为的多选类型url, 使用了config文件中request.multi_replace功能之后的URL
    private List<RequestUrlDynamicParamModel> dynamicParams = Collections.EMPTY_LIST;

    public RequestUrlModel(RequestModel higherLevel, String url) {
        super(higherLevel);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public boolean isHasDynamicParam() {
        return hasDynamicParam;
    }

    public void setHasDynamicParam(boolean hasDynamicParam) {
        this.hasDynamicParam = hasDynamicParam;
    }

    public boolean hasMultiParam() {
        return hasMultiParam;
    }

    public void setHasMultiParam(boolean hasMultiParam) {
        this.hasMultiParam = hasMultiParam;
    }

    public String getMultiUrl() {
        return multiUrl;
    }

    public void setMultiUrl(String multiUrl) {
        this.multiUrl = multiUrl;
    }

    public void addDynamicParam(RequestUrlDynamicParamModel dynamicParam) {
        if (dynamicParams == Collections.EMPTY_LIST) {
            this.dynamicParams = new ArrayList<>();
        }
        this.dynamicParams.add(dynamicParam);
    }

    public List<RequestUrlDynamicParamModel> getDynamicParams() {
        return dynamicParams;
    }

    /**
     * 转换了多选择参数的url：
     * 若有多选择参数，则需要转换，才能使用
     */
    public String request_normal_url() {
        if (hasMultiParam()) {
            return getMultiUrl();
        }
        return getUrl();
    }

    /**
     * 转换了id、date类型参数的url：
     * 1、若没有id或date类型参数，直接返回normal url;
     * 2、否则，获取到所有参数的位置，进行替换；
     */
    public String request_convert_url() {
        if (hasNotIdOrDateTypeParam()) {
            return request_normal_url();
        }
        return createConvertUrl();
    }

    private boolean hasNotIdOrDateTypeParam() {
        return dynamicParams.size() == 0;
    }

    private String createConvertUrl() {
        String url = request_normal_url();
        String replaceStr = ConfigWrapper.getRequest().getReplaceString();
        for (RequestUrlDynamicParamModel param : dynamicParams) {
            String replace = param.getParam();
            url = url.replace(replace, replaceStr);
        }
        return url;
    }
}
