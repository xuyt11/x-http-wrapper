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
    private boolean hasDynamicPath = false;
    private boolean hasMultiPath;// 是否有多选类型的参数，若有的话，则使用url时需要使用multiUrl
    private String multiUrl;// url经转换变为的多选类型url, 使用了config文件中request.multi_replace功能之后的URL
    private List<DynamicPathModel> dynamicPaths = Collections.EMPTY_LIST;

    public RequestUrlModel(RequestModel higherLevel, String url) {
        super(higherLevel);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public boolean isHasDynamicPath() {
        return hasDynamicPath;
    }

    public void setHasDynamicPath(boolean hasDynamicPath) {
        this.hasDynamicPath = hasDynamicPath;
    }

    public boolean hasMultiPath() {
        return hasMultiPath;
    }

    public void setHasMultiPath(boolean hasMultiPath) {
        this.hasMultiPath = hasMultiPath;
    }

    public String getMultiUrl() {
        return multiUrl;
    }

    public void setMultiUrl(String multiUrl) {
        this.multiUrl = multiUrl;
    }

    public void addDynamicPath(DynamicPathModel dynamicPath) {
        if (dynamicPaths == Collections.EMPTY_LIST) {
            this.dynamicPaths = new ArrayList<>();
        }
        this.dynamicPaths.add(dynamicPath);
    }

    public List<DynamicPathModel> getDynamicPaths() {
        return dynamicPaths;
    }

    /**
     * 转换了多选择参数的url：
     * 若有多选择参数，则需要转换，才能使用
     */
    public String request_normal_url() {
        if (hasMultiPath()) {
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
        if (hasNotIdOrDateTypePath()) {
            return request_normal_url();
        }
        return createConvertUrl();
    }

    private boolean hasNotIdOrDateTypePath() {
        return dynamicPaths.isEmpty();
    }

    private String createConvertUrl() {
        String url = request_normal_url();
        String replaceStr = ConfigWrapper.getRequest().getReplaceString();
        for (DynamicPathModel path : dynamicPaths) {
            String replace = path.getParam();
            url = url.replace(replace, replaceStr);
        }
        return url;
    }
}
