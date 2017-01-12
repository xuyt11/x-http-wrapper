package cn.ytxu.http_wrapper.config.property.api_data;

import cn.ytxu.http_wrapper.common.enums.ApiDataFileAddressType;
import cn.ytxu.http_wrapper.common.enums.ApiDataSourceType;

import java.util.Collections;
import java.util.List;

/**
 * api_data.json等api数据文件的地址信息，包括多操作系统的配置
 */
public class ApiDataBean {
    private String source = ApiDataSourceType.apidocjs.name();// API数据源的类型
    private String file_address_type = ApiDataFileAddressType.file.name();// API数据文件的地址类型：现在有file与url两种
    private List<ApiDataFilePathInfoBean> file_path_infos = Collections.EMPTY_LIST;// 多操作系统的配置
    private String file_charset = "UTF-8";// API数据文件中内容的字符集；

    public String getSource() {
        return source;
    }

    public String getFileAddressType() {
        return file_address_type;
    }

    public List<ApiDataFilePathInfoBean> getFilePathInfos() {
        return file_path_infos;
    }

    public String getFileCharset() {
        return file_charset;
    }

}
