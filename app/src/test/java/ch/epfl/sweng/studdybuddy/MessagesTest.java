package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.util.Messages;

import static org.junit.Assert.assertEquals;

public class MessagesTest {
    @Test
    public void groupID(){
        assertEquals("ch.epfl.sweng.studdybuddy.groupId", Messages.groupID);
    }

    @Test
    public void userID(){
        assertEquals("USERID", Messages.userID);
    }

    @Test
    public void maxUser(){
        assertEquals("MAX_NUMBER_OF_USERS_IN_GROUP", Messages.maxUser);
    }

    @Test
    public void test(){
        assertEquals("test", Messages.TEST);
    }
}
