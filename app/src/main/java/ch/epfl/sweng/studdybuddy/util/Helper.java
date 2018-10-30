package ch.epfl.sweng.studdybuddy.util;

import ch.epfl.sweng.studdybuddy.core.Pair;

public class Helper {

    static public String hashCode(Pair pair) {
        return Integer.toString((pair.getKey() + pair.getValue()).hashCode());
    }
}
