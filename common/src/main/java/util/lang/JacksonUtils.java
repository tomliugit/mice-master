package util.lang;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.util.*;

/**
 * ProjectName:soupai
 * PackageName:common.util.lang
 * Author :  WWQ1991
 * Date : 2018/2/5 14:07
 * Description :
 */
public class JacksonUtils {

    /**
     * 把一个普通对象转换为json格式的字符串
     *
     * @param obj
     * @param pretty
     * @return
     */
    public static String obj2JsonStr(Object obj, boolean pretty) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (pretty) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } else {
                return mapper.writeValueAsString(obj);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Set<String> jsonArray2Set(String jsonArrayStr) {
        Set<String> set = null;
        try {
            set = new LinkedHashSet<String>();
            JSONArray tmp = new JSONArray(jsonArrayStr);
            for (int i = 0; i < tmp.length(); i++) {
                if (!set.contains(tmp.get(i))) {
                    set.add(tmp.get(i).toString());
                }
            }
            return set;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json字符串转集合
     *
     * @param jsonArrayStr
     * @return
     */
    public static List<Map<String, Object>> jsonArray2List(String jsonArrayStr) {
        JSONArray tmp = new JSONArray(jsonArrayStr);
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < tmp.length(); i++) {
            String json = tmp.get(i).toString();
            if (json != null && !json.trim().isEmpty()) {
                Map<String, Object> temp = strToObj(json, Map.class);
                if (!data.contains(temp)) {
                    data.add(temp);
                }
            }
        }
        return data;
    }

    //字符串转对象
    public static <T> T strToObj(String result, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T t = mapper.readValue(result, clazz);
            return t;
        } catch (Exception e) {
        }
        return null;
    }
}
