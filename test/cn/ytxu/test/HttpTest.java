package cn.ytxu.test;

import cn.ytxu.http_wrapper.common.util.HttpRequest;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Administrator on 2016/12/27.
 */
public class HttpTest {


    @Test
    public void testGet() {
        try {
            String content = HttpRequest.sendGet("http://www.baidu.com");
            LogUtil.i("baidu content :" + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet4GBK() {
        try {
            String content = HttpRequest.sendGet("http://www.baidu.com", null, "GBK");
            LogUtil.i("baidu content :" + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
