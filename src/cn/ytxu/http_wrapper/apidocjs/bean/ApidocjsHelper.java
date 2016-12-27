package cn.ytxu.http_wrapper.apidocjs.bean;

import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldHelper;

/**
 * Created by Administrator on 2016/12/2.
 */
public class ApidocjsHelper {

    public static void reload() {
        ApiDataHelper.reload();
        FieldHelper.reload();
    }

    public static ApiDataHelper getApiData() {
        return ApiDataHelper.getInstance();
    }

    public static FieldHelper getField() {
        return FieldHelper.getInstance();
    }
}
