package cn.ytxu.test.json;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import com.google.gson.Gson;
import org.junit.Test;

/**
 * Created by ytxu on 16/12/29.
 */
public class GsonTest {

    @Test
    public void test() {
        String jsonStr = "{\"index\":1,\"text\":blank,\"isStop\":true}";
        GsonBean bean = new Gson().fromJson(jsonStr, GsonBean.class);
        LogUtil.i(bean.toString());
    }

    @Test
    public void test2() {
        String jsonStr = "{\"index\":1,\"text\":blank,\"isStop\":true, \"duoyue\":\"abs\"}";
        GsonBean bean = new Gson().fromJson(jsonStr, GsonBean.class);
        LogUtil.i(bean.toString());
    }

    @Test
    public void test3() {
        String jsonStr = "{\"index\":1,\"text\":blank,\"duoyue\":\"abs\"}";
        GsonBean bean = new Gson().fromJson(jsonStr, GsonBean.class);
        LogUtil.i(bean.toString());
    }

}

class GsonBean {
    int index;
    String text;
    boolean isStop;

    @Override
    public String toString() {
        return "GsonBean{" +
                "index=" + index +
                ", text='" + text + '\'' +
                ", isStop=" + isStop +
                '}';
    }
}