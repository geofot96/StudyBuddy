package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChatMessageTest {

    private static User user = new User( "Mr Potato", new ID<>("dumbid"));
    private String userName = user.getName();
    private String message = "Hello! How are you?";
    public long time = new Date().getTime();
    private ChatMessage testChatMessage = new ChatMessage(message, userName, time);

    private ChatMessage emptyChatMessage = new ChatMessage();

    @Test
    public void getMessageUser(){
        assertEquals("Mr Potato", testChatMessage.getMessageUser());
    }

    @Test
    public void setMessageUser(){
        emptyChatMessage.setMessageUser("Mr Tomato");
        assertEquals("Mr Tomato", emptyChatMessage.getMessageUser());
    }

    @Test
    public void getMessageText(){
        assertEquals("Hello! How are you?", testChatMessage.getMessageText());
    }

    @Test
    public void setMessageText(){
        emptyChatMessage.setMessageText("Let's go to study!");
        assertEquals("Let's go to study!", emptyChatMessage.getMessageText());
    }

    @Test
    public void getMessageTime(){
        assertEquals(time, testChatMessage.getMessageTime());
    }

    @Test
    public void setMessageTime(){
        emptyChatMessage.setMessageTime(time+1);
        assertEquals(time+1, emptyChatMessage.getMessageTime());
    }

    @Test
    public void differentTimes() throws InterruptedException {
        Thread.sleep(100);
        ChatMessage laterMessage = new ChatMessage(message, userName);
        assertNotEquals(time, laterMessage.getMessageTime());
    }
}