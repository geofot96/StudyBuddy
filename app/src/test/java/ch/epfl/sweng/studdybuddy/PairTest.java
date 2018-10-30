package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Pair;

import static org.junit.Assert.assertEquals;

public class PairTest {

    @Test
    public void initTest()
    {
        Pair p = new Pair("a","b");
        assertEquals(p.getKey(),"a");
        assertEquals(p.getValue(),"b");
    }

    @Test
    public void setKeyWorks(){
        Pair p = new Pair("a","b");
        p.setKey("c");
        p.setValue("m");
        assertEquals(p.getKey(),"c");
        assertEquals(p.getValue(),"m");

    }
    
}
