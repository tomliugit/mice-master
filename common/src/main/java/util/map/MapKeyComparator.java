package util.map;

import java.util.Comparator;

/**
 * Created by sunwell on 15/8/14.
 */
public class MapKeyComparator implements Comparator<String> {
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}