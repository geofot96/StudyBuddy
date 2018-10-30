package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.ID;

public class IDTest {


    @Test
    public void getSetIdTest() {
        ID<Integer> a = new ID<>("m");
        assert (a.getId().equals("m"));
        a.setId("z");
        assert (a.getId().equals("z"));

    }

    @Test
    public void copyTest() {
        ID<Integer> original = new ID<>("x");
        ID<Integer> copy = original.copy();
        assert(original.getId().equals(copy.getId()));

    }

    @Test
    public void hashCodeTest(){
        String s = "HCode";
        ID<Integer> original = new ID<>(s);
        assert(original.hashCode() == s.hashCode());

    }

    @Test
    public void toStringTest(){
        ID<Integer> original = new ID<>("foo");
        assert(original.toString().equals("foo"));
    }

   /*
    @Test
    public void equalsWorks(){
        ID<Integer> original = new ID<>("foo");
        ID<Integer> notOriginal = new ID<>("bar");
       // System.out.println(original.getId());
       // System.out.println(original.getId().equals(notOriginal.getId()));
        //assert(original.equals(original));
        assert(original.equals(notOriginal));

    }*/
}
