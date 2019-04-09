package util.lang;

import util.regex.RegexValidateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunwell on 2017/3/30.
 */
public class ArrayListUtils {


    //去重list
    public static List<String> removeRepeatedItem(List<String> li) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < li.size(); i++) {
            String str = li.get(i);  //获取传入集合对象的每一个元素
            if (!list.contains(str)) {   //查看新集合中是否有指定的元素，如果没有则加入
                list.add(str);
            }
        }
        return list;  //返回集合
    }

    /**
     * 拆分集合
     *
     * @param <T>
     * @param resList 要拆分的集合
     * @param count   每个集合的元素个数
     * @return 返回拆分后的各个集合
     */
    public static <T> List<List<T>> split(List<T> resList, int count) {

        if (resList == null || count < 1)
            return null;
        List<List<T>> ret = new ArrayList<List<T>>();
        int size = resList.size();
        if (size <= count) { //数据量不足count指定的大小
            ret.add(resList);
        } else {
            int pre = size / count;
            int last = size % count;
            //前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<T>();
                for (int j = 0; j < count; j++) {
                    itemList.add(resList.get(i * count + j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if (last > 0) {
                List<T> itemList = new ArrayList<T>();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * count + i));
                }
                ret.add(itemList);
            }
        }
        return ret;

    }


    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    public static boolean isContain(List<String> stringList, String s) {
        for (String str : stringList) {
            if (str.contains(s) || s.contains(str)) {//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
                return true;//查找到了就返回真，不在继续查询
            }
        }
        return false;//没找到返回false
    }

    public static int pageCount(int recordCount, int perPage) {
        int pc = (int) Math.ceil(recordCount / (double) perPage);
        return (pc == 0) ? 1 : pc;
    }

    public static boolean hasInCondition(String[] condition, String path) {
        boolean has = false;
        for (String url : condition) {
            if (path.indexOf(url) == 0) {
                has = true;
                break;
            }
        }
        return has;
    }

    public static boolean hasInCondition2(String[] condition, String key) {
        boolean has = false;
        for (String item : condition) {
            if (key.equals(item)) {
                has = true;
                break;
            }
        }
        return has;
    }

    public static List<String> listOrderBy(String orderBy) {
        List<String> list = new ArrayList<>();
        if (orderBy != null) {
            Map<String, Object> sortMap = GsonUtils.json2map(orderBy);
            for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue() + "";
                if (!RegexValidateUtils.isInjectSql(key) && !RegexValidateUtils.isInjectSql(value)) {
                    list.add(key + " " + value);
                }
            }
        }
        return list;
    }


}
