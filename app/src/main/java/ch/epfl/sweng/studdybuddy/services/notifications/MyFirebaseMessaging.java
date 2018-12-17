package ch.epfl.sweng.studdybuddy.services.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.ChatActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static java.lang.Math.max;


public class MyFirebaseMessaging extends FirebaseMessagingService {
    public static final String NO_GROUP = "";
    private static String currentGroupID = NO_GROUP;

    public static void setCurrentGroupID(String currentGroupID) {
        MyFirebaseMessaging.currentGroupID = currentGroupID;
    }

    public static String getCurrentGroupID() {
        return currentGroupID;
    }

    private String title;
    private String body;
    private String click_action;
    private String groupID;
    private PendingIntent resultPendingIntent;
    private Uri defaultSound;

    private NotifFactory notifFactory = new NotifFactory();

    private Version version = new Version();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        title = notification.getTitle();
        body = notification.getBody();
        click_action = notification.getClickAction();
        groupID = remoteMessage.getData().get("group_id");

        Intent resultIntent = new Intent(this, ChatActivity.class);
        resultIntent.setAction(click_action);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        Bundle bundle = new Bundle();
        bundle.putString(Messages.groupID, groupID);
        bundle.putString(Messages.course, title);
        resultIntent.putExtras(bundle);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if(!groupID.equals(currentGroupID)) {
            if (version.getBuildVersion() >= Build.VERSION_CODES.O) {
                sendOreoNotification();
            } else {
                sendNotification();
            }
        }
    }

    private void sendOreoNotification() {
        OreoNotification oreoNotification = notifFactory.OreoNotificationFactory(this);
        Notification.Builder builder = oreoNotification.getOreoNotification(title, body, resultPendingIntent, defaultSound, R.mipmap.ic_launcher);

        int mNotificationID = (int) System.currentTimeMillis();
        oreoNotification.getNotificationManager().notify(mNotificationID, builder.build());
    }

    private void sendNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(resultPendingIntent);

        int mNotificationId = (int) System.currentTimeMillis();
        NotificationManager mNotifyMgr = notifFactory.nManagerFactory(this);

        mNotifyMgr.notify(mNotificationId, builder.build());
    }

    public void setNotifFactory(NotifFactory factory) {
        this.notifFactory = factory;
    }

    public void setVersion(Version version){
        this.version = version;
    }
}
