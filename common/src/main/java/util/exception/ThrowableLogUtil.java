package util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kasia
 * @date: Created in
 * @modified By:
 * @description:
 */
public class ThrowableLogUtil {

    private Map<String, Object> map;

    private ThrowableLogUtil() {
        map = new HashMap<>();
    }

    public static ThrowableLogUtil get(){
        return new ThrowableLogUtil();
    }

    /**
     * @author kasia
     * @date: Created in
     * @modified By:
     * @description: 获取异常的堆栈信息
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

    public ThrowableLogUtil addMap(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
