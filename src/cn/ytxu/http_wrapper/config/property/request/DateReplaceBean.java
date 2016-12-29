package cn.ytxu.http_wrapper.config.property.request;

/**
 * url中，date参数的格式与在输出文件中在url上请求参数的名称
 */
public class DateReplaceBean {
    private String date_format;// date参数在url上的格式
    private String date_request_param;// 在输出文件中，在url上请求参数的名称

    public String getDate_format() {
        return date_format;
    }

    public String getDate_request_param() {
        return date_request_param;
    }

}