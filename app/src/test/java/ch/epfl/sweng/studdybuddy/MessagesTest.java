package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.util.Messages;

import static org.junit.Assert.assertEquals;

public class MessagesTest {
    private Messages m = new Messages();
    @Test
    public void groupID(){
        assertEquals("ch.epfl.sweng.studdybuddy.groupId", m.groupID);
    }

    @Test
    public void userID(){
        assertEquals("USERID", m.userID);
    }

    @Test
    public void maxUser(){
        assertEquals("MAX_NUMBER_OF_USERS_IN_GROUP", m.maxUser);
    }

    @Test
    public void test(){
        assertEquals("test", m.TEST);
    }
}
