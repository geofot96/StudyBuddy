package ch.epfl.sweng.studdybuddy.util;

import ch.epfl.sweng.studdybuddy.ID;
import ch.epfl.sweng.studdybuddy.Pair;

public class Helper {

    static public String hashCode(Pair pair) {
        return Integer.toString((pair.getKey() + pair.getValue()).hashCode());
    }
}
