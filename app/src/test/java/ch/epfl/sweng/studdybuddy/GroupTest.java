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

    @Test
    public void comparetoWorks(){
        Group a = new Group(10,new Course("CLP"), "EN");
        assert(a.compareTo(g) == -1);
        assert(g.compareTo(a) == 1);
    }
}
