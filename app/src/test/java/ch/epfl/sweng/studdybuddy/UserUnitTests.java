package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;

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

   /* @Test
    public void getCurrentGroupIfNull(){
        user.setCurrentGroups(null);
        assertEquals(null, user.getCurrentGroups());
    }

    @Test
    public void getCurrentGroup(){
        User user1 = new User("1", "Mr Potato", "IN", new ArrayList<>(), new ArrayList<>());
        assertEquals(new ArrayList<>(), user.getCurrentGroups());
    }*/
}
