package util.net;

import eu.bitwalker.useragentutils.UserAgent;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by sunwell on 2017/3/30.
 */
public class IpUtils {
    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddress = null;
        // ipAddress = request.getRemoteAddr();
        //获取客户端真实IP--------
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
            //------
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取浏览器 系统、类型、版本
     *
     * @param request request
     * @return map
     */
    public static String getUserAgent(HttpServletRequest request) {
        try {
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            String operationSystem = userAgent.getOperatingSystem().getName();
            String browserName = userAgent.getBrowser().getName();
            String browserVersion = userAgent.getBrowserVersion().getMajorVersion();
            return operationSystem + "|" + browserName + "|" + browserVersion;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String transMapToString(Map map) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "," : "");
        }
        return sb.toString();
    }


    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }


    /**
     * 解析IP信息
     *
     * @param ip
     * @return
     */
    public static Map<String, String> getIpDetail(String ip) {
        Map<String, String> data = new HashMap<>();
        try {
            String value = ipLookUp(ip);
            if (value == null || value.trim().isEmpty()) {
                data.put("country", "中国");
                data.put("province", "未知");
                data.put("city", "未知");
                data.put("carrier", "未知");
                return data;
            }
            JSONObject jb = JSONObject.fromObject(value);
            JSONArray jsonArray = jb.getJSONArray("data");
            if (jsonArray != null && jsonArray.size() > 0) {
                data.put("country", jsonArray.getString(0));
                data.put("province", jsonArray.getString(1));
                data.put("city", jsonArray.getString(2));
                data.put("carrier", jsonArray.getString(3));
            } else {
                data.put("country", "中国");
                data.put("province", "未知");
                data.put("city", "未知");
                data.put("carrier", "未知");
            }
        } catch (Exception e) {
            data.put("country", "中国");
            data.put("province", "未知");
            data.put("city", "未知");
            data.put("carrier", "未知");
        }
        return data;
    }

    private static String ipLookUp(String ip) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL("http://api.ip138.com/query/?ip=" + ip + "&datatype=jsonp&token=f4a0ae8cd2bf61f36731ade5c6743c90");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
