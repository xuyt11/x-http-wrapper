package cn.ytxu.http_wrapper.template.file.model;

import java.util.List;

/**
 * xhwt文件，中生成文件的路径与名称；
 * 2016-12-11
 */
public class XHWTFileModel {
    private String file_name;// 文件的名称
    private List<XHWTFileDirModel> file_dirs;

    public String getFileName() {
        return file_name;
    }

    public List<XHWTFileDirModel> getFileDirs() {
        return file_dirs;
    }
}
