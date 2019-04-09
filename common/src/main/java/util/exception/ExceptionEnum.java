package util.exception;

public enum ExceptionEnum {

    SYSTEM_ERROR("5000","系统错误",new BaseException("5000","系统错误")),
    NPE_EXCEPTION("5001","空指针异常",new BaseException("5001","空指针异常")),
    CHECK_EXCEPTION("5002","参数校验异常",new ParameterException("5002","参数校验异常")),
    JDBC_EXCEPTION("5003","JDBC操作异常",new BaseException("5003","JDBC操作异常")),
    LOGICAL_EXCEPTION("5004","内容逻辑异常",new BaseException("5004","内容逻辑异常"));

    private String code;
    private String msg;
    private BaseException baseException;

    ExceptionEnum(String code, String msg,BaseException baseException) {
        this.code = code;
        this.msg = msg;
        this.baseException=baseException;
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

    public BaseException getBaseException() {
        return baseException;
    }

    public void setBaseException(BaseException baseException) {
        this.baseException = baseException;
    }
}
