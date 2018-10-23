package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class UserUnitTests
{
    private static ArrayList<String> list= new ArrayList<String>(Arrays.asList("aa","bb"));

    private static User user = new User( "Mr Potato", new ID<>("dumbid"),  list);
    private  static ID id=new ID("someId");

    @Test
    public void testGetNameWorksCorrectly()
    {
        User user1 = new User( "Mr Potato", new ID<>("dumbid"), list);
        assertEquals("Mr Potato", user1.getName());
    }

    @Test
    public void TestSetNameWorksCorrectly()
    {
        user.setName("Geo");
        assertEquals("Geo", user.getName());
    }

    @Test
    public void testEmptyConstructor()
    {
        User x=new User();

    }
    @Test
    public void testGetCoursesPreset()
    {
        User user1 = new User( "Mr Potato", new ID<>("dumbid"), list);
        assertEquals(list, user1.getCoursesPreset());
    }
    @Test
    public void testSetCoursesPreset()
    {
        user.setCoursesPreset(list);
        assertEquals(list, user.getCoursesPreset());
    }
    @Test
    public void testSetUserID()
    {
        user.setUserID(id);
        assertEquals(id, user.getUserID());
    }
    @Test
    public void testGetUserID()
    {
        User user1 = new User( "Mr Potato", id, list);
        assertEquals(id, user1.getUserID());
    }


}
