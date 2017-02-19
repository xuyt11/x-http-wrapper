package cn.ytxu.http_wrapper.config.property.template_file_info;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.util.TextUtil;
import cn.ytxu.http_wrapper.template.file.type.XHWTFileType;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/12/14.
 */
public class TemplateFileInfoWrapper {
    private static TemplateFileInfoWrapper instance;

    /**
     * json配置文件的路径
     */
    private String xhwtConfigPath;
    private LinkedHashMap<String, TemplateFileInfoBean> templateFileInfos;

    public static TemplateFileInfoWrapper getInstance() {
        return instance;
    }

    public static void load(String xhwtConfigPath, LinkedHashMap<String, TemplateFileInfoBean> templateFileInfos) {
        LogUtil.i(TemplateFileInfoWrapper.class, "load template file info property start...");
        instance = new TemplateFileInfoWrapper(xhwtConfigPath, templateFileInfos);
        LogUtil.i(TemplateFileInfoWrapper.class, "load template file info property success...");
    }

    private TemplateFileInfoWrapper(String xhwtConfigPath, LinkedHashMap<String, TemplateFileInfoBean> templateFileInfos) {
        this.xhwtConfigPath = xhwtConfigPath;
        this.templateFileInfos = templateFileInfos;

        if (Objects.isNull(templateFileInfos) || templateFileInfos.isEmpty()) {
            throw new IllegalArgumentException("u must setup template file info property...");
        }

        templateFileInfos.keySet().forEach(templateFileTypeName -> XHWTFileType.get(templateFileTypeName));

        if (!hasValidTemplateFileInfo(templateFileInfos)) {
            throw new IllegalArgumentException("no template file can be parsed, u must check the template file info property settting...");
        }
    }

    private boolean hasValidTemplateFileInfo(LinkedHashMap<String, TemplateFileInfoBean> templateFileInfos) {
        for (TemplateFileInfoBean templateFileInfo : templateFileInfos.values()) {
            if (isValidFileInfo(templateFileInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidFileInfo(TemplateFileInfoBean templateFileInfo) {
        if (Objects.isNull(templateFileInfo) || !templateFileInfo.isNeedGenerate()) {
            return false;
        }
        return hasValidFiles(templateFileInfo);
    }

    private boolean hasValidFiles(TemplateFileInfoBean templateFileInfo) {
        List<String> paths = templateFileInfo.getPaths();
        if (Objects.isNull(paths) || paths.isEmpty()) {
            return false;
        }

        for (String path : paths) {
            if (isValidFile(path)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidFile(String path) {
        if (TextUtil.isBlank(path)) {
            return false;
        }

        File templateFile = new File(path);
        if (templateFile.isAbsolute()) {
            return templateFile.exists();
        }

        File dir = new File(xhwtConfigPath).getParentFile();
        templateFile = new File(dir, path);
        return templateFile.exists();
    }

    public boolean needParseTheTemplateFile(XHWTFileType tFileType) {
        TemplateFileInfoBean tFileInfo = getTemplateFileInfoBean(tFileType);
        return isValidFileInfo(tFileInfo);
    }

    private TemplateFileInfoBean getTemplateFileInfoBean(XHWTFileType tFileType) {
        return templateFileInfos.get(tFileType.name());
    }

    /**
     * @throws NonNeedParseTheTemplateFileException
     */
    public List<String> getTemplateFileAbsolutePaths(XHWTFileType tFileType) throws NonNeedParseTheTemplateFileException {
        if (!needParseTheTemplateFile(tFileType)) {
            throw new NonNeedParseTheTemplateFileException();
        }

        TemplateFileInfoBean tFileInfo = getTemplateFileInfoBean(tFileType);
        List<String> paths = tFileInfo.getPaths();
        List<String> targetPaths = new ArrayList<>(paths.size());
        for (String path : paths) {
            if (isValidFile(path)) {
                targetPaths.add(getTemplateFileAbsolutePath(path));
            }
        }
        return targetPaths;
    }

    private String getTemplateFileAbsolutePath(String path) {
        File templateFile = new File(path);
        if (templateFile.isAbsolute()) {
            return templateFile.getAbsolutePath();
        }

        File dir = new File(xhwtConfigPath).getParentFile();
        templateFile = new File(dir, path);
        return templateFile.getAbsolutePath();
    }

    public static final class NonNeedParseTheTemplateFileException extends RuntimeException {
    }

    public boolean isPolymerization(XHWTFileType tFileType) {
        TemplateFileInfoBean tFileInfo = getTemplateFileInfoBean(tFileType);
        return tFileInfo.isPolymerization();
    }
}
