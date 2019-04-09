package util.lang;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author sunwell
 */
public class GsonUtils {

    private static Gson gson = new Gson();

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    /**
     * 对象转字符串
     *
     * @param object
     * @return
     */
    public static String object2json(Object object) {
        Gson includeNullsGson = gsonBuilder.serializeNulls().create();
        return includeNullsGson.toJson(object);
    }

    /**
     * 字符串转对象
     *
     * @param str
     * @param cls
     * @return
     */
    public static Object json2object(String str, Class<?> cls) {
        return gson.fromJson(str, cls);
    }

    public static Map<String, Object> json2map(String str) {

        Map<String, Object> map = gson.fromJson(str, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map;
    }

    /**
     * 复杂对象转Json
     *
     * @param list
     * @return
     */
    public static String tojson(List<?> list, Type type) {
        return gson.toJson(list, type);
    }

    /**
     * Json转复杂对象
     *
     * @param json
     * @param type
     * @return
     */
    public static Object fromjson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    /**
     * Json 转 List
     *
     * @param json
     * @return
     */
    public static List<?> json2list(String json, Type type) {
        return gson.fromJson(json, type);
    }


    public static <T> T toBean(String json, Class<T> clz) {

        return gson.fromJson(json, clz);
    }

    public static <T> Map<String, T> toMap(String json, Class<T> clz) {
        Map<String, JsonObject> map = gson.fromJson(json, new TypeToken<Map<String, JsonObject>>() {
        }.getType());
        Map<String, T> result = new HashMap<>();
        for (String key : map.keySet()) {
            result.put(key, gson.fromJson(map.get(key), clz));
        }
        return result;
    }

    public static Map<String, Object> toMap(String json) {
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        return map;
    }

    public static Map<String, Long> toMap_long(String json) {
        Map<String, Long> map = gson.fromJson(json, new TypeToken<Map<String, Long>>() {
        }.getType());
        return map;
    }

    public static <T> List<T> toList(String json, Class<T> clz) {
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        List<T> list = new ArrayList<>();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, clz));
        }
        return list;
    }

}
