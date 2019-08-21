package ldh.common.spring.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Puhui on 2016/4/29.
 */
public class HttpUtil {

    private static HttpClient httpClient = HttpClients.custom().build();

    public static String post(String url, Map<String, Object> dataMap) throws Exception {
        //设置客户端编码
        if (httpClient == null) {
            // Create HttpClient Object
            httpClient = HttpClients.custom().build();
        }

        // Post请求
        HttpPost httppost = new HttpPost(url);

        // 设置参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> data : dataMap.entrySet()) {
            params.add(new BasicNameValuePair(data.getKey(), data.getValue().toString()));
        }
        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        //设置报文头
        httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        // 发送请求
        HttpResponse httpresponse = httpClient.execute(httppost);
        // 获取返回数据
        HttpEntity entity = httpresponse.getEntity();
        String body = EntityUtils.toString(entity);
        return body;
    }

    public static String body(String url, String json) throws IOException {
        //设置客户端编码
        if (httpClient == null) {
            // Create HttpClient Object
            httpClient = HttpClients.custom().build();
        }

        // Post请求
        HttpPost httppost = new HttpPost(url);

        if (json != null) {
            // 设置参数
            httppost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
        }

        //设置报文头
        httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        // 发送请求
        HttpResponse httpresponse = httpClient.execute(httppost);
        // 获取返回数据
        HttpEntity entity = httpresponse.getEntity();
        if (entity.getContentLength() == 0L) {
            return null;
        }
        String body = EntityUtils.toString(entity);
        return body;
    }
}
