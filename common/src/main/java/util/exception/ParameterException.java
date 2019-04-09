package util.exception;

public class ParameterException extends BaseException {

    private String code;
    private String msg;

    public ParameterException() {
    }

    public ParameterException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ParameterException(Integer code) {
        super(ExceptionEnum.SYSTEM_ERROR.getMsg());
        this.code = code.toString();
    }

    public ParameterException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
