package util.exception;

/**
 * @author sunwell
 */
public class ExceptionMsg {

    public static final String REQUIRED_PARAMETER_ERROR = "E100001";
    /**
     * 系统提示
     */
    public static final String SYSTEM_HINT = "E100002";
    public static final String INTERNAL_SERVER_ERROR = "E100000";

    private String code;
    private String msg;

    public ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * account-service 异常码
     */
    public static class AccountExceptionCode {
        /**
         * 免费版无权限
         */
        public static final String EDITION_FREE_FORBIDDEN = "E200000";

        /**
         * Access_token不存在
         */
        public static final String ACCESS_TOKEN_NON_EXISTENT = "E200010";
    }
}