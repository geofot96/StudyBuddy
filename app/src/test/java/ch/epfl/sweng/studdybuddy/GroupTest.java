package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.UUID;

import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;

import static org.junit.Assert.assertEquals;

public class GroupTest {


    private static Group groupFactory() {
        return new Group(10,new Course("CLP"), "EN", "123");
    }
    private static Group g = groupFactory();

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
    public void setGetMaxNoUsers() {
        g.setMaxNoUsers(3);
        assertEquals(3, g.getMaxNoUsers());
    }

    @Test
    public void setGetCourse() {
        g.setCourse(new Course("not clp"));
        assertEquals(g.getCourse().getCourseName(), "not clp");
    }

    @Test
    public void setAdminWorks() {
        g.setAdminID("gg");
        assertEquals(g.getAdminID(), "gg");
    }

    @Test
    public void comparetoWorks(){
        Group a = groupFactory();
        try {
            Thread.sleep(800);
        }catch (Exception e){
            System.out.println("Error during thread sleep: " + e.getMessage());
        }
        Group b = groupFactory();
        assert(a.compareTo(b) == 1);
        assert(b.compareTo(a) == -1);
        assert(a.compareTo(a) == 0);
    }
}
