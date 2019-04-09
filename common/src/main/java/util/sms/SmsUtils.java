package util.sms;

import constant.GlobalConstants;
import util.http.HttpClientUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sunwell
 */
public class SmsUtils {
    public static boolean sendSms(String mobile, String content) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("mobile", mobile);
        parameters.put("message", content);
        // 2018/3/28 需要新的campaign  签名为[大猫] 100317、功能性短信 100318、营销性短信
        parameters.put("campaign", "100317");
        String res = HttpClientUtil.post(GlobalConstants.Sms.SMS_POST_URL, parameters);
        if (res.contains("发送失败")) {
            //  2018/9/28 记录日志 异常后再次发送
            res = HttpClientUtil.post(GlobalConstants.Sms.SMS_POST_URL, parameters);
            return !res.contains("发送失败");
        }
        return true;
    }

    public static void main(String[] args) {
        sendSms("18516760685", "这是发给我的");
    }
}
