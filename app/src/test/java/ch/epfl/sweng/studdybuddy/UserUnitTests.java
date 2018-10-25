package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserUnitTests
{
    private static User user = new User( "Mr Potato", new ID<>("dumbid"));

    @Test
    public void getNameWorksCorrectly()
    {
        User user1 = new User( "Mr Potato", new ID<>("dumbid"));
        assertEquals("Mr Potato", user1.getName());
    }

    @Test
    public void setNameWorksCorrectly()
    {
        user.setName("Geo");
        assertEquals("Geo", user.getName());
    }

    @Test
    public void setUserIDWorksCorrectly(){

        user.setUserID(new ID<User>("foobar"));
        assert(user.getUserID().getId().equals("foobar"));
    }

}
