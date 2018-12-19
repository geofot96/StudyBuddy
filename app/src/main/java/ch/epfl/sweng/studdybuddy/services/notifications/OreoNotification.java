package ch.epfl.sweng.studdybuddy.services.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;
import android.util.Pair;

public class OreoNotification extends ContextWrapper {

    private static final String CHANNEL_ID = "ch.epfl.sweng.studdybuddy";
    private static final String CHANNEL_NAME = "studdybuddy";

    private NotificationManager notificationManager;

    private static NotifFactory notifFactory = new NotifFactory();
    private static ChannelFactory channelFactory = new ChannelFactory();
    private static Version versionGetter = new Version();

    public static void setNotifFactory(NotifFactory factory) {
        notifFactory = factory;
    }

    public static void setChannelFactory(ChannelFactory factory){
        channelFactory = factory;
    }

    public static void setVersion(Version version){
        versionGetter = version;
    }

    public OreoNotification(Context base) {
        super(base);
        if(versionGetter.getBuildVersion() >= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = channelFactory.getNotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(false);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getNotificationManager().createNotificationChannel(channel);
    }

    public NotificationManager getNotificationManager() {
        if(notificationManager == null){
            notificationManager = notifFactory.nManagerFactory(this);
        }
        return notificationManager;
    }


    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getOreoNotification(Pair<String, String> titleBody, PendingIntent pendingIntent, Uri soundUri, int icon){
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle(titleBody.first)
                .setContentText(titleBody.second)
                .setSmallIcon(icon)
                .setSound(soundUri)
                .setAutoCancel(true);

    }
}
