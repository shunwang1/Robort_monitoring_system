package cn.iua.caas.robotdatahub.keys;

public class ApiKeys {
    private static final String AMAP_WEB_SERVICE_API_URL = "https://restapi.amap.com/v3/geocode/regeo";
    private static final String WEB_SERVICE_API_KEY = "3076fae4da503f0086697b9dffb89127"; // 高德的web服务端API Key

    public static String getWebServiceApiUrl() {
        return AMAP_WEB_SERVICE_API_URL;
    }

    public static String getWebServiceApiKey() {
        return WEB_SERVICE_API_KEY;
    }
}
