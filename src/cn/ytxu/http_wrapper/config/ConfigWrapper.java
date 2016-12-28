package cn.ytxu.http_wrapper.config;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.property.api_data.ApiDataWrapper;
import cn.ytxu.http_wrapper.config.property.base_config.BaseConfigWrapper;
import cn.ytxu.http_wrapper.config.property.filter.FilterWrapper;
import cn.ytxu.http_wrapper.config.property.param_type.ParamTypeWrapper;
import cn.ytxu.http_wrapper.config.property.response.ResponseWrapper;
import cn.ytxu.http_wrapper.config.property.request.RequestWrapper;
import cn.ytxu.http_wrapper.config.property.status_code.StatusCodeWrapper;
import cn.ytxu.http_wrapper.config.property.template_file_info.TemplateFileInfoWrapper;
import com.google.gson.Gson;

import java.io.*;

/**
 * Created by ytxu on 2016/8/14.
 */
public class ConfigWrapper {

    public static void load(final String xhwtConfigPath) {
        BufferedReader reader = null;
        try {
            LogUtil.i(ConfigWrapper.class, "init config file:(" + xhwtConfigPath + ") start...");

            reader = FileUtil.getReader(xhwtConfigPath, "UTF-8");
            ConfigBean configData = new Gson().fromJson(reader, ConfigBean.class);
            load(xhwtConfigPath, configData);

            LogUtil.i(ConfigWrapper.class, "init config file:(" + xhwtConfigPath + ") success...");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i(ConfigWrapper.class, "init config file:(" + xhwtConfigPath + ") failure...");
        } finally {
            FileUtil.closeReader(reader);
        }
    }

    private static void load(String xhwtConfigPath, ConfigBean configData) {
        ApiDataWrapper.load(configData.getApiData());
        TemplateFileInfoWrapper.load(xhwtConfigPath, configData.getTemplateFileInfos());
        BaseConfigWrapper.load(configData.getBaseConfig());
        FilterWrapper.load(configData.getFilter());
        RequestWrapper.load(configData.getRequest());
        ResponseWrapper.load(configData.getResponse());
        StatusCodeWrapper.load(configData.getStatusCode());
        ParamTypeWrapper.load(configData.getParamTypes());
    }

    public static ApiDataWrapper getApiDataFile() {
        return ApiDataWrapper.getInstance();
    }

    public static TemplateFileInfoWrapper getTemplateFileInfo() {
        return TemplateFileInfoWrapper.getInstance();
    }

    public static BaseConfigWrapper getBaseConfig() {
        return BaseConfigWrapper.getInstance();
    }

    public static FilterWrapper getFilter() {
        return FilterWrapper.getInstance();
    }

    public static RequestWrapper getRequest() {
        return RequestWrapper.getInstance();
    }

    public static ResponseWrapper getResponse() {
        return ResponseWrapper.getInstance();
    }

    public static StatusCodeWrapper getStatusCode() {
        return StatusCodeWrapper.getInstance();
    }

    public static ParamTypeWrapper getParamType() {
        return ParamTypeWrapper.getInstance();
    }

}
