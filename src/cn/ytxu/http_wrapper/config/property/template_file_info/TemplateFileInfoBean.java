package cn.ytxu.http_wrapper.config.property.template_file_info;

import java.util.List;

/**
 * Created by ytxu on 2016/12/14.
 * x-http-wrapper中模板文件的信息
 */
public class TemplateFileInfoBean {
    /**
     * 是否需要根据temp文件去生成http相关文件
     */
    private boolean need_generate = true;

    /**
     * temp文件的路径：支持同文件夹下的文件名，或是绝对路径
     * 可以设置多个模板文件
     */
    private List<String> paths;

    /**
     * 是否需要聚合数据
     */
    private boolean is_polymerization = false;

    public boolean isNeedGenerate() {
        return need_generate;
    }

    public List<String> getPaths() {
        return paths;
    }

    public boolean isPolymerization() {
        return is_polymerization;
    }

}
