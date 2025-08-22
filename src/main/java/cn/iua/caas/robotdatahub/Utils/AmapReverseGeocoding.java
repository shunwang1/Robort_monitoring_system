package cn.iua.caas.robotdatahub.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.iua.caas.robotdatahub.Entity.robotLocation.Location;
import cn.iua.caas.robotdatahub.keys.ApiKeys;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * 此工具为高德地图经纬度解析工具，将经纬度分析为具体省，市区，县级，乡镇和综合地址并且封装到了自定义location。
 */
public class AmapReverseGeocoding {



    public static Location getAddressByLocation(String longitude, String latitude) {
        StringBuilder result = new StringBuilder();
        Location location = new Location();
        try {
            // 构建请求URL
            String urlStr = ApiKeys.getWebServiceApiUrl() + "?location=" + longitude + "," + latitude
                    + "&key=" + ApiKeys.getWebServiceApiKey() + "&extensions=all";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 获取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();

            // 解析JSON响应
            JSONObject jsonObject = JSON.parseObject(result.toString());
            if ("1".equals(jsonObject.getString("status"))) {
                JSONObject regeocode = jsonObject.getJSONObject("regeocode");
                JSONObject addressComponent = regeocode.getJSONObject("addressComponent");

                // 提取各级行政区域信息
                String province = addressComponent.getString("province");
                String city = addressComponent.getString("city");
                String district = addressComponent.getString("district");
                String township = addressComponent.getString("township");
                String formattedAddress = regeocode.getString("formatted_address");
                location.setLatitude(Double.parseDouble(latitude));
                location.setLongitude(Double.parseDouble(longitude));
                location.setCityName(city);
                location.setDistrictName(district);
                location.setProvinceName(province);
                location.setTownShipName(township);
                location.setFullAddress(formattedAddress);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return location;
    }
}
