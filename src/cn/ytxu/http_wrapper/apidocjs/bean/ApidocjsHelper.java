package cn.ytxu.http_wrapper.apidocjs.bean;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldHelper;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
public class ApidocjsHelper {

    public static void reload() {
        ApiDataHelper.reload();
        FieldHelper.reload();
    }

    public static List<ApiDataBean> getApiDatasFromFile() throws IOException {
        // 1 get api_data.json path
        String apiDataPath = ConfigWrapper.getApiDataFile().getApiDataFilePath();
        String charset = ConfigWrapper.getApiDataFile().getFileCharset();
        LogUtil.i("api Data Path:" + apiDataPath);
        // 2 get json data from file
        String apiDataJsonStr = FileUtil.getContent(apiDataPath, charset);
        LogUtil.i("api Data length:" + apiDataJsonStr.length());
        // 3 get java object array by json data
        JsonReader reader = new JsonReader(new StringReader(apiDataJsonStr));
        reader.setLenient(true);
        Gson gson = new Gson();
        return gson.fromJson(reader, new TypeToken<List<ApiDataBean>>() {
        }.getType());
    }

    public static ApiDataHelper getApiData() {
        return ApiDataHelper.getInstance();
    }

    public static FieldHelper getField() {
        return FieldHelper.getInstance();
    }
}
