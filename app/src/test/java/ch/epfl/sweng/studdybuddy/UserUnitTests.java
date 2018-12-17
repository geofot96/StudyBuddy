package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        user.setUserID(new ID<>("foobar"));
        assert(user.getUserID().getId().equals("foobar"));
    }

    @Test
    public void equalsTest(){
        User u = new User("a", new ID<>("1"));
        User u2 = new User("b", new ID<>("1"));
        User u3 = new User("b", new ID<>("2"));
        User u4 = new User("a", new ID<>("1"));
        u.setFavoriteLanguage("a");
        u2.setFavoriteLanguage("b");
        u3.setFavoriteLocation(MapsHelper.ROLEX_LOCATION);
        assertTrue(u.equals(u));
        assertFalse(u.equals(u2));
        assertFalse(u.equals(u3));
        assertFalse(u2.equals(u3));
        assertFalse(u.equals(u4));

    }

    @Test
    public void setAsTest(){
        User u = new User("a", "b");
        u.setAs(user);
        assertEquals(u, user);
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
