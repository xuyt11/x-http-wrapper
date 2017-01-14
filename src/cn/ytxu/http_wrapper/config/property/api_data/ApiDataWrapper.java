package cn.ytxu.http_wrapper.config.property.api_data;

import cn.ytxu.http_wrapper.common.enums.ApiDataFilePathType;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.util.OSPlatform;
import cn.ytxu.http_wrapper.common.enums.ApiDataSourceType;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/9/2.
 */
public class ApiDataWrapper {

    private ApiDataBean apiDataBean;

    private static ApiDataWrapper instance;

    public static ApiDataWrapper getInstance() {
        return instance;
    }

    public static void load(ApiDataBean apiDataFileBean) {
        if (Objects.isNull(apiDataFileBean)) {
            throw new RuntimeException("u don`t setup api_data property");
        }
        LogUtil.i(ApiDataWrapper.class, "load api data file property start...");
        instance = new ApiDataWrapper(apiDataFileBean);
        LogUtil.i(ApiDataWrapper.class, "load api data file property success...");
    }

    private ApiDataWrapper(ApiDataBean apiDataFileBean) {
        this.apiDataBean = apiDataFileBean;
        judgeApiDataSource();
        judgeApiDataFilePathType();
        judgeApiDataFilePath();
    }

    private void judgeApiDataSource() {
        String apiDataSource = apiDataBean.getSource();
        if (Objects.isNull(apiDataSource)) {
            throw new RuntimeException("u don`t setup source...");
        }
        ApiDataSourceType.get(apiDataSource);
    }

    private void judgeApiDataFilePathType() {
        String apiDataFilePathType = apiDataBean.getFilePathType();
        if (Objects.isNull(apiDataFilePathType)) {
            throw new RuntimeException("u don`t setup file_path_type...");
        }
        ApiDataFilePathType.get(apiDataFilePathType);
    }

    private void judgeApiDataFilePath() {
        List<ApiDataFilePathInfoBean> pathInfos = apiDataBean.getFilePathInfos();
        if (Objects.isNull(pathInfos) || pathInfos.isEmpty()) {
            throw new RuntimeException("u don`t setup file_path_infos");
        }

        final String osName = OSPlatform.getCurrentOSPlatform().getOsName();
        boolean notFindFileInfoInCurrentOS = true;
        for (ApiDataFilePathInfoBean pathInfo : pathInfos) {
            if (osName.equalsIgnoreCase(pathInfo.getOSName())) {
                judgePrivateFilePathType(pathInfo);
                notFindFileInfoInCurrentOS = false;
                break;
            }
        }
        if (notFindFileInfoInCurrentOS) {
            throw new IllegalArgumentException("not found the match file_path_info, and the current os name is "
                    + osName);
        }
    }

    private void judgePrivateFilePathType(ApiDataFilePathInfoBean pathInfo) {
        boolean hasPrivatePathType = pathInfo.hasPrivatePathType();
        if (hasPrivatePathType) {
            ApiDataFilePathType.get(pathInfo.getPathType());
        }
    }


    public String getApiDataSource() {
        return apiDataBean.getSource();
    }

    public String getApiDataFilePathType() {
        ApiDataFilePathInfoBean filePathInfo = getFilePathInfoInCurrOS();
        boolean hasPrivatePathType = filePathInfo.hasPrivatePathType();
        if (hasPrivatePathType) {
            return filePathInfo.getPathType();
        }
        return apiDataBean.getFilePathType();
    }

    public String getApiDataFilePath() {
        return getFilePathInfoInCurrOS().getPath();
    }

    private ApiDataFilePathInfoBean getFilePathInfoInCurrOS() {
        List<ApiDataFilePathInfoBean> pathInfos = apiDataBean.getFilePathInfos();
        final String osName = OSPlatform.getCurrentOSPlatform().getOsName();
        for (ApiDataFilePathInfoBean pathInfo : pathInfos) {
            if (osName.equalsIgnoreCase(pathInfo.getOSName())) {
                return pathInfo;
            }
        }
        throw new IllegalArgumentException("not found the match file_path_info, and the current os name is "
                + osName);
    }

    public String getFileCharset() {
        return apiDataBean.getFileCharset();
    }
}
