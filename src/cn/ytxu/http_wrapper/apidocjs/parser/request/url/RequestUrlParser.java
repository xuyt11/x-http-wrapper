package cn.ytxu.http_wrapper.apidocjs.parser.request.url;

import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.config.property.request.DateReplaceBean;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.request.url.DynamicPathModel;
import cn.ytxu.http_wrapper.model.request.url.RequestUrlModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/8/14.
 */
public class RequestUrlParser {
    private static final Pattern ID_OR_DATE_PATTERN = Pattern.compile("[\\{]{1}.{2,}?[\\}]{1}");
    private static final Pattern MULTI_PATTERN = Pattern.compile("[\\[]{1}.{2,}?[\\]]{1}");

    private final RequestUrlModel requestUrl;

    public RequestUrlParser(RequestModel request) {
        requestUrl = request.getUrl();
    }

    public void start() {
        checkAndSetupHasDynamicParam2RequestUrlModel();
        parseMultiUrl();
        parseIdOrDateParam();
    }

    private void checkAndSetupHasDynamicParam2RequestUrlModel() {
        Matcher m = ID_OR_DATE_PATTERN.matcher(requestUrl.getUrl());
        requestUrl.setHasDynamicPath(m.find());
    }

    private void parseMultiUrl() {
        Matcher m = MULTI_PATTERN.matcher(requestUrl.getUrl());
        boolean hasMultiParam = m.find();
        requestUrl.setHasMultiPath(hasMultiParam);

        if (!hasMultiParam) {
            return;
        }

        String multiUrl = requestUrl.getUrl();
        do {
            String group = m.group();
            multiUrl = getMultiUrl(multiUrl, group);
        } while (m.find());
        requestUrl.setMultiUrl(multiUrl);
    }

    private String getMultiUrl(String multiUrl, String group) {
        List<String> multis = ConfigWrapper.getRequest().getMultis();
        for (String multi : multis) {
            if (group.contains(multi)) {
                return multiUrl.replace(group, multi);// 直接替换requestUrl，不需要进行参数的添加等的处理
            }
        }
        throw new NotFoundTargetMultiUrlReplaceContentException(group);
    }

    private static class NotFoundTargetMultiUrlReplaceContentException extends RuntimeException {
        public NotFoundTargetMultiUrlReplaceContentException(String group) {
            super("you must add this multi replace content in .json config file, and this multi pattern group is " + group);
        }
    }

    private void parseIdOrDateParam() {
        if (!requestUrl.isHasDynamicPath()) {// no heed parse
            return;
        }

        String url = requestUrl.hasMultiPath() ? requestUrl.getMultiUrl() : requestUrl.getUrl();
        Matcher m = ID_OR_DATE_PATTERN.matcher(url);
        int paramIndex = 0;
        while (m.find()) {
            getDynamicParamModel(m, paramIndex);
            paramIndex++;
        }
    }

    private DynamicPathModel getDynamicParamModel(Matcher m, int paramIndex) {
        int start = m.start();
        int end = m.end();
        String group = m.group();

        String realParam = getRealParam(group);
        if (realParam.contains("-") || realParam.contains(":") || realParam.contains(" ")) {
            throw new RuntimeException("the request url is " + requestUrl.getUrl() +
                    "\n, and the real param is " + realParam +
                    "\n, and ytxu need parse this param, so i throw exception...");
        }
        return new DynamicPathModel(requestUrl, group, realParam, paramIndex, start, end);
    }

    private String getRealParam(String group) {
        String realParam = group.substring(1, group.length() - 1);
        List<DateReplaceBean> dateReplaces = ConfigWrapper.getRequest().getDateReplaces();
        for (DateReplaceBean dateReplace : dateReplaces) {
            if (dateReplace.getDate_format().equals(realParam)) {
                realParam = dateReplace.getDate_request_param();
                break;
            }
        }
        return realParam;
    }

}
