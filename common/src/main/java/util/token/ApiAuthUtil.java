package util.token;


import constant.GlobalConstants;
import util.security.DES;
import util.security.MD5;

public class ApiAuthUtil {
    public static String genAuthCode(String account) {
        try {
            // 先将用户名、密码、WEBKEY，进行MD5加密
            String step1 = MD5.toMD5(account + "#" + GlobalConstants.ENCRYPT_KEY_FOR_API);
            // 将step1信息经DES加密
            return DES.encrypt(step1, GlobalConstants.ENCRYPT_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(genAuthCode("29"));
    }

}
