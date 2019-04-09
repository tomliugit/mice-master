package util.token;

import constant.GlobalConstants;
import util.security.DES;
import util.security.MD5;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by sunwell on 2017/3/30.
 */
public class TokenUtils {

    public static String simpleToken(String userId, String password) {
        return MD5.toMD5(userId + "#" + password + "#" + GlobalConstants.ENCRYPT_KEY);
    }

    public static String genToken(String userId, String password) {
        try {
            // 先将用户名、密码、WEBKEY，进行MD5加密
            String step1 = MD5.toMD5(userId + "#" + password + "#" + GlobalConstants.ENCRYPT_KEY) + "#" + userId;
            // 将step1信息经DES加密
            String step2 = DES.encrypt(step1, GlobalConstants.ENCRYPT_KEY);
            return step2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decodeToken(String token) throws Exception {
        return DES.decrypt(token, GlobalConstants.ENCRYPT_KEY);
    }


    public static String getUidByToken(String token) {
        String userId = null;
        try {
            String encodeToken = decodeToken(token);
            String[] arr = encodeToken.split("#");
            if (arr.length == 2) {
                userId = arr[1];
            }
        } catch (Exception e) {
        }
        return userId;
    }


}
