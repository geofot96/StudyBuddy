package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.services.chat.ChatMessage;

import static org.junit.Assert.assertEquals;

public class ChatMessageTest {

    private static User user = new User( "Mr Potato", new ID<>("dumbid"));
    private String userName = user.getName();
    private String message = "Hello! How are you?";
    private String uri = "firebase.com";
    public long time = new Date().getTime();
    private ChatMessage testChatMessage = new ChatMessage(message, userName, uri);

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
        long time = new Date().getTime();
        emptyChatMessage.setMessageTime(time);
        assertEquals(time, emptyChatMessage.getMessageTime());
    }
    @Test
    public void getMessageUri(){
        assertEquals("firebase.com", testChatMessage.getImageUri());
    }

    @Test
    public void setMessageUri(){
        emptyChatMessage.setImageUri("travis.com");
        assertEquals("travis.com", emptyChatMessage.getImageUri());
    }
}