package ch.epfl.sweng.studdybuddy.util;

import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studdybuddy.core.Pair;

public final class Helper {


    private Helper() {}

    static public String hashCode(Pair pair) {
        return Integer.toString((pair.getKey() + pair.getValue()).hashCode());
    }

    public static int getOrDefault(String key, Map<String, Integer> map) {
        if(map.containsKey(key)) return map.get(key);
        else return 0;
    }

    public static void safeAddId(String ref, String neu, String val, List<String> ids) {
        if(ref != null && neu != null && ids != null && ref.equals(neu) && !ids.contains(val)) {
            ids.add(val);
        }
    }
}
