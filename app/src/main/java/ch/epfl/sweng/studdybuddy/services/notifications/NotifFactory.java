package ch.epfl.sweng.studdybuddy.services.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.NotificationCompat;

public class NotifFactory {

    public NotificationManager nManagerFactory(ContextWrapper context){
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public OreoNotification OreoNotificationFactory(Context base){
        return new OreoNotification(base);
    }

    public NotificationCompat.Builder getBuilder(Context context) {
        return new NotificationCompat.Builder(context);
    }
}
