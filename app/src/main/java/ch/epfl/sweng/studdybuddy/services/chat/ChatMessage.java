package ch.epfl.sweng.studdybuddy.services.chat;

import java.util.Date;

/**
 * A class representing a chat message object
 */
public class ChatMessage {

    private String messageText;
    private String messageUser;
    private String imageUri;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser,String imageUri) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.imageUri=imageUri;

        // Initialize to current time
        messageTime = new Date().getTime();
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
