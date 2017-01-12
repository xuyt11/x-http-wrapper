package cn.ytxu.http_wrapper.config.property.api_data;

import java.util.Objects;

/**
 * api_data数据文件的多操作系统配置
 */
public class ApiDataFilePathInfoBean {
    private String OSName;
    private String address;
    private String file_address_type;// API数据文件的地址类型(ApiDataFileAddressType)：现在有file与url两种

    public String getOSName() {
        return OSName;
    }

    public String getAddress() {
        return address;
    }

    public String getFileAddressType() {
        return file_address_type;
    }

    public boolean hasOwnerFileAddressType() {
        return Objects.nonNull(file_address_type);
    }
}
