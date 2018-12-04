package ch.epfl.sweng.studdybuddy.services.chat;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser) {
        // Initialize to current time
        this(messageText, messageUser, new Date().getTime());
    }

    public ChatMessage(String messageText, String messageUser, long messageTime){
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
    }

    public ChatMessage(){

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
