package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

public class GroupTest {

    private static Group g = new Group(10,new Course("CLP"), "EN");



    @Test
    public void setGroupIDWorks(){
        ID<Group> id = new ID<>("mock");
        g.setGroupID(id);
        assert(g.getGroupID().getId().equals(id.getId()));
    }

    @Test
    public void setLangWorks(){
        g.setLang("FR");
        assert(g.getLang().equals("FR"));
    }
/*
    @Test
    public void comparetoWorks(){
        Group a = new Group(10,new Course("CLP"), "EN");
        try {
            Thread.sleep(800);
        }catch (Exception e){
            System.out.println("Error during thread sleep: " + e.getMessage());
        }
        Group b = new Group(a);
        assert(a.compareTo(b) == 1);
        assert(b.compareTo(a) == -1);
        assert(a.compareTo(a) == 0);
    }*/
}
