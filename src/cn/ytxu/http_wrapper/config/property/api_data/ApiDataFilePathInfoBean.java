package cn.ytxu.http_wrapper.config.property.api_data;

import java.util.Objects;

/**
 * api_data数据文件的多操作系统配置
 */
public class ApiDataFilePathInfoBean {
    private String os_name;
    private String path;
    private String path_type;// private path type, API数据文件的地址类型(ApiDataFilePathType)：现在有file与url两种

    public String getOSName() {
        return os_name;
    }

    public String getPath() {
        return path;
    }

    public String getPathType() {
        return path_type;
    }

    public boolean hasPrivatePathType() {
        return Objects.nonNull(path_type);
    }
}
