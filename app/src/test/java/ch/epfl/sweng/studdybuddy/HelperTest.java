package ch.epfl.sweng.studdybuddy;


import org.junit.Test;

import ch.epfl.sweng.studdybuddy.util.Helper;

import static org.junit.Assert.assertEquals;

public class HelperTest {
    @Test
    public void pairHashcodeEqualsHashcodeOfBothPairs() {
        Helper h = new Helper();
        assertEquals(h.hashCode(new Pair("a", "b")), Integer.toString(("a" + "b").hashCode()));
    }
}
