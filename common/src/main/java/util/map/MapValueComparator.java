package util.map;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by sunwell on 15/8/14.
 */
public class MapValueComparator implements Comparator<Map.Entry<String, String>> {
    public int compare(Map.Entry<String, String> me1, Map.Entry<String, String> me2) {
        return me1.getValue().compareTo(me2.getValue());
    }
}