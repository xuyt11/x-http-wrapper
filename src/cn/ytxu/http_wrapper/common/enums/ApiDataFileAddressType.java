package cn.ytxu.http_wrapper.common.enums;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.common.util.HttpRequest;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;

import java.io.IOException;

/**
 * Created by ytxu on 2016/12/27.
 * API数据源的文件地址类型
 */
public enum ApiDataFileAddressType {
    network {
        @Override
        public String getApiDataContentText() throws IOException {
            // 1 get api_data.json url
            String apiDataUrl = ConfigWrapper.getApiDataFile().getApiDataFilePath();
            String charset = ConfigWrapper.getApiDataFile().getFileCharset();
            LogUtil.i("api Data Url:" + apiDataUrl);
            String apiDataJsonStr = HttpRequest.sendGet(apiDataUrl, null, charset);
            LogUtil.i("api Data length:" + apiDataJsonStr.length());
            return apiDataJsonStr;
        }
    },
    file {
        @Override
        public String getApiDataContentText() throws IOException {
            // 1 get api_data.json path
            String apiDataPath = ConfigWrapper.getApiDataFile().getApiDataFilePath();
            String charset = ConfigWrapper.getApiDataFile().getFileCharset();
            LogUtil.i("api Data Path:" + apiDataPath);
            // 2 get json data from file
            String apiDataJsonStr = FileUtil.getContent(apiDataPath, charset);
            LogUtil.i("api Data length:" + apiDataJsonStr.length());
            return apiDataJsonStr;
        }
    };

    public abstract String getApiDataContentText() throws IOException;

    public static ApiDataFileAddressType get(String name) {
        for (ApiDataFileAddressType apiDataFileAddressType : ApiDataFileAddressType.values()) {
            if (apiDataFileAddressType.name().equals(name)) {
                return apiDataFileAddressType;
            }
        }
        throw new IllegalArgumentException("don`t found this api data file address type:" + name);
    }
}
