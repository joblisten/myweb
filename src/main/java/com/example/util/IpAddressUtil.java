package com.example.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
@Slf4j
@Component
public class IpAddressUtil {

    /**
     *响应返回的格式为
     * {"status":"success","country":"中国","countryCode":"CN",
     * "region":"GD","regionName":"广东","city":"广州市","zip":"",
     * "lat":23.1181,"lon":113.2539,"timezone":"Asia/Shanghai","
     * isp":"CHINANET Guangdong province Guangzhou MAN network",
     * "org":"Chinanet GD","as":"AS134773 CHINANET Guangdong province Guangzhou MAN network",
     * "query":"119.131.77.2"}
     *
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) {
        String s = sendGet(ip);
        Map map = JSONObject.parseObject(s, Map.class);
        if (map.get("status").equals("success")) {
            String country = (String) map.get("country");
            String regionName = (String) map.get("regionName");
            String city = (String) map.get("city");
            String address = country + "-" + regionName + "-" + city;
            return address;
        } else {
            log.info("status{}",map.get("status"));
            return null;
        }
    }


    public static String sendGet(String ip) {
        String result = "";
        BufferedReader in = null;
        try {
           //根据ip获取地区
            String url = "http://ip-api.com/json/" + ip + "?lang=zh-CN";
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送get请求失败");
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}


