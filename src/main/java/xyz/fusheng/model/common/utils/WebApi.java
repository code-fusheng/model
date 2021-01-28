package xyz.fusheng.model.common.utils;

/**
 * @FileName: MallCooUtils
 * @Author: code-fusheng
 * @Date: 2021/1/18 6:47 下午
 * @Version: 1.0
 * @Description:
 */


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 猫酷开放平台接入示例
 * Class WebApi
 * @package tool\mallcoo
 */
public class WebApi {

    private static WebApi webApi = null;
    private String publicKey = "1ULYkA";
    private String privateKey = "e14c4430ac63f427";
    private String AppID = "60054ccc4ba1f24c2c93f2e6";

    public static WebApi getIns() {
        if (webApi == null) {
            webApi = new WebApi();
        }
        return webApi;
    }

    /**
     * 设置header参数
     * @param conn
     * @param jsonParameter
     */
    private void setHeadr(HttpsURLConnection conn, String jsonParameter) {
        if (jsonParameter == null) {
            jsonParameter = "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = simpleDateFormat.format(new Date());

        String timeStamp = dateStr;
        String encryptString = "{publicKey:" + publicKey + ",timeStamp:" + timeStamp + ",data:" + jsonParameter + ",privateKey:" + privateKey + "}";
        String sign = DigestUtils.md5DigestAsHex(encryptString.getBytes()).substring(8,24).toUpperCase();

        conn.setRequestProperty("AppID", AppID);
        conn.setRequestProperty("TimeStamp", timeStamp);
        conn.setRequestProperty("PublicKey", publicKey);
        conn.setRequestProperty("Sign", sign);
    }



    /**
     * 方式2：
     * 使用HttpClient进行接口请求
     * @param url 请求地址
     * @param jsonParameter 请求json参数
     * @return
     */
    public String doPost(String url, String jsonParameter) {
        String charset = "UTF-8";
        String result = null;
        try {
            SSLClient httpClient = new SSLClient();

            HttpPost httpPost = new HttpPost(url);
            setHeadr(httpPost, jsonParameter);

            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(jsonParameter, charset);
            stringEntity.setContentEncoding(charset);

            httpPost.setEntity(stringEntity);

            System.out.println("Executing request " + httpPost.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {//
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            result = httpClient.execute(httpPost, responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 设置header信息
     * @param httpPost
     * @param jsonParameter
     */
    private void setHeadr(HttpPost httpPost, String jsonParameter) {
        if (jsonParameter == null) {
            jsonParameter = "";
        }
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Connection", "Keep-Alive");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = simpleDateFormat.format(new Date());

        String timeStamp = dateStr;
        String encryptString = "{publicKey:" + publicKey + ",timeStamp:" + timeStamp + ",data:" + jsonParameter + ",privateKey:" + privateKey + "}";
        //md5加密
        String sign = DigestUtils.md5DigestAsHex(encryptString.getBytes()).substring(8,24).toUpperCase();
        httpPost.setHeader("AppID", AppID);
        httpPost.setHeader("TimeStamp", timeStamp);
        httpPost.setHeader("PublicKey", publicKey);
        httpPost.setHeader("Sign", sign);
    }

    public static void main(String[] args) {
        String result;
        System.out.println("==================不带参调用======================");
        String url = "https://openapi10.mallcoo.cn/Parking/V1/GetMemberCardNo/";
        result = WebApi.getIns().doPost(url, "");
        System.out.println("HttpClient方式返回值:" + result);

        System.out.println("==================带参调用======================");
        url = "https://openapi10.mallcoo.cn/Parking/V1/GetMemberCardNo/";
        JSONObject object = new JSONObject();

        object.put("parkCode", "210");
        object.put("plateNo", "京PVJ516".getBytes(StandardCharsets.UTF_8));

        result = WebApi.getIns().doPost(url, object.toString());
        System.out.println("HttpClient方式返回值:" + result);


//        System.out.println("==================自定义调用======================");
//        url = "https://openapi10.mallcoo.cn/Parking/V1/GetMemberCardNo/";
//        HttpUtils.sendPost(url, object.toString());


    }




}




