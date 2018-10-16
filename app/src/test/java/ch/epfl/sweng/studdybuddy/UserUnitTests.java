package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserUnitTests
{
    private static User user = new User("1", "Mr Potato", "IN", null, null);

    @Test
    public void getNameWorksCorrectly()
    {
        User user1 = new User("1", "Mr Potato", "IN", null, null);
        assertEquals("Mr Potato", user1.getName());
    }

    @Test
    public void getSectionWorksCorrectly()
    {
        User user1 = new User("1", "Mr Potato", "IN", null, null);
        assertEquals("IN", user1.getSection());
    }


    @Test
    public void setNameWorksCorrectly()
    {
        user.setName("Geo");
        assertEquals("Geo", user.getName());
    }

    @Test
    public void setSectionWorksCorrectly()
    {
        user.setSection("MA");
        assertEquals("MA", user.getSection());
    }

}
