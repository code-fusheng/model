package xyz.fusheng.model.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.geoip.model.v20200101.DescribeIpv4LocationRequest;
import com.aliyuncs.geoip.model.v20200101.DescribeIpv4LocationResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @FileName: AddressUtils
 * @Author: code-fusheng
 * @Date: 2020/9/1 15:32
 * @version: 1.0
 * Description: 根据IP地址获取真实地址的工具类（调用淘宝查询接口）
 */


public class AddressUtils {

    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static final String TENCENT_IP_LOCATION_API = "https://apis.map.qq.com/ws/location/v1/ip";

    public static final String TENCENT_KEY = "CRLBZ-HYXKR-D6WWV-WJYJJ-4NJX5-7FBFP";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }

        // 调用接口发送请求
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        JSONObject obj = JSONObject.parseObject(rspStr);
        JSONObject data = obj.getObject("data", JSONObject.class);
        String region = data.getString("region");
        String city = data.getString("city");
        address = region + " " + city;
        return address;
    }



    /**
     * 腾讯IP定位 API
     */
    public static String getIpAddressInfo(String ip) {
        String address = "XX XX";
        if (ip.equals("127.0.0.1")) {
            return address;
        }
        String responseStr = "";
        try {
            responseStr = HttpUtils.sendGet(TENCENT_IP_LOCATION_API, "ip=" + ip + "&" + "key=" + TENCENT_KEY);
        } catch (Exception e) {
            log.info("IP:{}地址解析异常:{}", ip, e);
            return address;
        }
        if (StringUtils.isNotBlank(responseStr)) {
            JSONObject responseObject = JSON.parseObject(responseStr);
            String status = responseObject.getString("status");
            // 获取结果对象
            JSONObject result = responseObject.getObject("result", JSONObject.class);
            // 地理位置信息
            JSONObject location = result.getObject("location", JSONObject.class);
            // 经度
            String lng = location.getString("lng");
            // 纬度
            String lat = location.getString("lat");
            // 地址信息
            JSONObject adInfo = result.getObject("ad_info", JSONObject.class);
            address = adInfo.getString("nation") + " " + adInfo.getString("province")
                    + " " + adInfo.getString("city") + " " + adInfo.getString("district");
        }
        log.info("IP:{}地址返回结果:{},解析结果:{}", ip, address, responseStr);
        return address;

    }

    /**
     * 阿里云获取 IP 地址信息
     */
    public static void getIpV4Location() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GE6g186CuWjoarrq4nH", "ioL3ZwSVd5OLbj5L7I2pmzHsg6GXLY");
        IAcsClient client = new DefaultAcsClient(profile);
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        request.setPageSize(10);
        DescribeInstancesResponse response;
        try {
            response = client.getAcsResponse(request);
            for (DescribeInstancesResponse.Instance instance:response.getInstances()) {
                System.out.println(instance.getPublicIpAddress());
            }
            log.info("打印IP解析结果:{}", new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static void describeIpv4Location(String address) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GJuqXFNuFxgVmobNw7J", "rNjBbYWEVrBqlcDoVX78tigX5njhDX");
        IAcsClient client = new DefaultAcsClient(profile);

        DescribeIpv4LocationRequest request = new DescribeIpv4LocationRequest();
        request.setRegionId("cn-hangzhou");
        request.setIp("221.206.131.10");

        try {
            DescribeIpv4LocationResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            log.info("打印IP解析结果:{}", new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }





    class responseData {

    }





}
