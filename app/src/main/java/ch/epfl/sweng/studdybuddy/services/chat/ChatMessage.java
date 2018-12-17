package ch.epfl.sweng.studdybuddy.services.chat;

import java.util.Date;

/**
 * A class representing a chat message object
 */
public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;

//TODO, after the merge
    public ChatMessage(String messageText, String messageUser) {
        // Initialize to current time
        this(messageText, messageUser, new Date().getTime());
    }

    public ChatMessage(String messageText, String messageUser, long messageTime) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
    }

    public ChatMessage() {

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
