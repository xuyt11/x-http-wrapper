package cn.ytxu.http_wrapper.template.file.model;

/**
 * 2016-12-11
 */
public class XHWTFileDirModel {
    private String os_name;// 对应系统的名称,必须要与OSPlatform中的一致
    private String path;// 文件夹的路径字符串

    public String getOsName() {
        return os_name;
    }

    public String getPath() {
        return path;
    }
}