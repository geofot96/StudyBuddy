package ch.epfl.sweng.studdybuddy.services.notifications;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.messaging.RemoteMessage;

public class RemoteNotification {
    private RemoteMessage remoteMessage;
    private RemoteMessage.Notification throwable;

    public RemoteNotification(RemoteMessage remoteMessage, @Nullable RemoteMessage.Notification throwable){
        this.remoteMessage = remoteMessage;
        this.throwable = throwable;
    }


    public RemoteMessage.Notification getNotification() {
        RemoteMessage.Notification notif = remoteMessage.getNotification();
        if(notif == null){
            notif = throwable;
        }
        return notif;
    }
}
