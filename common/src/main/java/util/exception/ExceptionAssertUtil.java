package util.exception;

public class ExceptionAssertUtil {

    public static void isTrue(Boolean flag){
        if (flag){
            throw new BaseException();
        }
    }
    public static void isTrue(Boolean flag,String code,String msg){
        if (flag){
            for (ExceptionEnum e : ExceptionEnum.values()) {
                if (code.equals(e.getCode())){
                    BaseException exception=e.getBaseException();
                    exception.setMsg(msg);
                    throw exception;
                }
            }
            throw new BaseException(code,msg);
        }
    }
    public static void isTrue(Boolean flag,String msg){
        if (flag){
            throw new BaseException(msg);
        }
    }
}
