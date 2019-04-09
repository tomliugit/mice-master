package util.wx;

import util.http.HttpClientUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunwell
 */
public class WxUtil {

    public static Map<String, String> getTicket() {
        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb5e26820fac779d0&secret=74cf97cd45ce3515e2552c9f36b13305";
        String res = HttpClientUtil.get(accessTokenUrl);
        JSONObject jb = JSONObject.fromObject(res);
        String accessToken = jb.getString("access_token");
        Map<String, String> map = new HashMap<String, String>();
        map.put("accessToken", accessToken);
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        String res1 = HttpClientUtil.get(ticketUrl);
        JSONObject jb1 = JSONObject.fromObject(res1);

        String ticket = jb1.getString("ticket");
        map.put("ticket", ticket);
        return map;
    }
}
