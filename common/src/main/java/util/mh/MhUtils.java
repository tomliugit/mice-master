package util.mh;

import util.http.HttpClientUtil;

/**
 * @author sunwell
 */
public class MhUtils {
    private static String MH_HOST = "http://apin.meihua.info/";
    private static final String MH_USER_ID = MH_HOST + "u/%1s/user_id";

    public static Long getUserId(String userName) {
        String url = String.format(MH_USER_ID, userName);

        String res = HttpClientUtil.get(url);
        if (res != null) {
            return Long.valueOf(res);
        }
        return 0L;
    }


    public static void main(String[] args) {
        System.out.println(getUserId("sunwellgu"));
    }

}
