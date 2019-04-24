package constant;

/**
 * @author sunwell
 * @date 2019/01/03
 */
public class GlobalConstants {
    /***************************************************************************************************
     * 当Production时，请将DEBUG改为: 3;isPro改为true
     * 1: 本地
     * 2: 测试
     * 3: 生产
     *
     *************************************************************************************************/
    public static final int ENV_DEV = 1;
    public static final int ENV_QA = 2;
    public static final int ENV_PRO = 3;
    public static final int ENV_PRE_PRO = 4;
    public static final int ENV_DEV_P = 5;

    public static final String ENCRYPT_KEY = "48169527";
    public static final String ENCRYPT_KEY_FOR_API = "MeiHua@002";

    public static final String ENCRYPT_KEY_FOR_WEB_SOCKET_SESSION = "MHWebSocket";
    public static final String REQ_USER_AGENT = "MH-WebHook-Server";

    public static final Integer REDIS_DB_INDEX_MH = 9;

    public static final String DEFAULT_FILE_PATH = "/data/static/upload/";
    public static final String DEFAULT_FILE_DOWNLOAD_TMP_PATH = "download_tmp/";

    public static final String DEFAULT_MYSQL_P_MASTER = "10.9.123.219";
    public static final String DEFAULT_MYSQL_P_SLAVE = "10.9.133.68";

    public static final String DEFAULT_MYSQL_Q_MASTER = "10.9.123.219";
    public static final String DEFAULT_MYSQL_Q_SLAVE = "10.9.133.68";

    public static final String DEFAULT_MYSQL_P_MASTER_DEV = "106.75.31.202";
    public static final String DEFAULT_MYSQL_P_SLAVE_DEV = "117.50.12.32";

    public static final String DEFAULT_MYSQL_Q_MASTER_DEV = "106.75.31.202";
    public static final String DEFAULT_MYSQL_Q_SLAVE_DEV = "117.50.12.32";

    public static final int DEFAULT_MYSQL_READ = 1;
    public static final int DEFAULT_MYSQL_WRITER = 0;


    public static final String DEFAULT_PROJECT_PRE = "mice_project_";


    public static class Mail {
        public static final String HOST = "mail.meihua.info";
        public static final int PORT = 25;
        public static final String USERNAME = "sender2@meihua.info";
        public static final String PASSWORD = "Bei*(8vb&a3#";
        public static final String SENDER_NAME = "梅花信息 - bigcat.com";
    }

    public static class Sms {
        /**
         * 吉信通账户信息
         */
        public static final String SMS_POST_URL = "http://onepassport.meihua.info/OnePassportService.asmx/sendsinglemessage";
        public static final String SMS_UID = "shmeihua";
        public static final String SMS_PWD = "meihuainfo";

        public static final String CONTENT_MOBILE_BIND_MSG = "您的验证码是%1s，该验证码用于梅花网手机验证。如非本人操作，请忽略。";

        public static final Integer EXPIRE_MINUTE_MOBILE_BIND_MSG = 30;
    }

    public static class Host {
        public static String mice(Integer evn) {
            String r;
            switch (evn) {
                case 1:
                    r = "http://mice.meihua.info";
                    break;
                case 2:
                    r = "";
                    break;
                case 4:
                    r = "";
                    break;
                case 3:
                    r = "";
                    break;
                default:
                    r = "";
                    break;
            }
            return r;
        }

        public static String dbIp(Integer env, Boolean read) {
            if (env == GlobalConstants.ENV_DEV) {
                return read ? GlobalConstants.DEFAULT_MYSQL_Q_SLAVE_DEV : GlobalConstants.DEFAULT_MYSQL_Q_MASTER_DEV;
            }
            if (env == GlobalConstants.ENV_DEV_P) {
                return read ? GlobalConstants.DEFAULT_MYSQL_P_SLAVE_DEV : GlobalConstants.DEFAULT_MYSQL_P_MASTER_DEV;
            }
            if (env == GlobalConstants.ENV_QA) {
                return read ? GlobalConstants.DEFAULT_MYSQL_Q_SLAVE : GlobalConstants.DEFAULT_MYSQL_Q_MASTER;
            }
            if (env == GlobalConstants.ENV_PRO) {
                return read ? GlobalConstants.DEFAULT_MYSQL_P_SLAVE : GlobalConstants.DEFAULT_MYSQL_P_MASTER;
            }
            if (env == GlobalConstants.ENV_PRE_PRO) {
                return read ? GlobalConstants.DEFAULT_MYSQL_P_SLAVE : GlobalConstants.DEFAULT_MYSQL_P_MASTER;
            }
            return "";
        }
    }
}
