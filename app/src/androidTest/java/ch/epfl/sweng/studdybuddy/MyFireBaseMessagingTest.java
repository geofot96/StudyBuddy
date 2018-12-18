package ch.epfl.sweng.studdybuddy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.services.notifications.MyFirebaseMessaging;
import ch.epfl.sweng.studdybuddy.services.notifications.NotifFactory;
import ch.epfl.sweng.studdybuddy.services.notifications.OreoNotification;
import ch.epfl.sweng.studdybuddy.services.notifications.Version;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyFireBaseMessagingTest {

    private NotifFactory notifFactory = mock(NotifFactory.class);
    private MyFirebaseMessaging classTested;
    private RemoteMessage.Builder remoteMessageBuilder = new RemoteMessage.Builder("test");
    private RemoteMessage remoteMessageCorrectGroup;
    private RemoteMessage remoteMessageWrongGroup;

    private OreoNotification mockOreo = mock(OreoNotification.class);
    private NotificationManager mockManager = mock(NotificationManager.class);
    private Notification.Builder notifOreoBuilder = mock(Notification.Builder.class);
    private Version versionOreo = mock(Version.class);
    private Version versionBase = mock(Version.class);

    private RemoteMessage.Notification mockedNotification = mock(RemoteMessage.Notification.class);
    private Intent intent = mock(Intent.class);
    private TaskStackBuilder stackBuilder = mock(TaskStackBuilder.class);
    private NotificationCompat.Builder builder = mock(NotificationCompat.Builder.class);

    @Before
    public void setup(){
        remoteMessageBuilder.addData("group_id", Messages.TEST);
        remoteMessageCorrectGroup = remoteMessageBuilder.build();
        remoteMessageBuilder.addData("group_id", "wrongGp");
        remoteMessageWrongGroup = remoteMessageBuilder.build();
        classTested = new MyFirebaseMessaging();
        when(notifFactory.nManagerFactory(any())).thenReturn(mockManager);
        when(notifFactory.OreoNotificationFactory(any())).thenReturn(mockOreo);
        when(notifFactory.getBuilder(any())).thenReturn(builder);
        when(mockOreo.getNotificationManager()).thenReturn(mockManager);
        when(versionOreo.getBuildVersion()).thenReturn(Build.VERSION_CODES.O);
        when(versionBase.getBuildVersion()).thenReturn(Build.VERSION_CODES.BASE);
        when(mockOreo.getOreoNotification(any(), any(), any(), anyInt())).thenReturn(notifOreoBuilder);

        when(builder.setSmallIcon(anyInt())).thenReturn(builder);
        when(builder.setContentTitle(any())).thenReturn(builder);
        when(builder.setContentText(any())).thenReturn(builder);
        when(builder.setAutoCancel(true)).thenReturn(builder);
        when(builder.setSound(any())).thenReturn(builder);
        when(builder.setContentIntent(any())).thenReturn(builder);


        classTested.setNotifFactory(notifFactory);
        classTested.setCurrentGroupID(Messages.TEST);
    }

    @Test
    public void correctlySetUpGroup(){
        assertEquals(Messages.TEST, classTested.getCurrentGroupID());
    }

    @Test
    public void createOreoNotification(){
        createNotification(versionOreo, 1, 0);
    }

    @Test
    public void createRegularNotification(){
        createNotification(versionBase, 0, 1);
    }

    @Test
    public void noNotificationIfChattingWithTheGroup(){
        classTested.setUp(mockedNotification, remoteMessageCorrectGroup, intent, stackBuilder);
        verify(notifFactory, times(0)).OreoNotificationFactory(any());
        verify(notifFactory, times(0)).nManagerFactory(any());
    }


    private void createNotification(Version versioning, int a, int b) {
        classTested.setVersion(versioning);
        classTested.setUp(mockedNotification, remoteMessageWrongGroup, intent, stackBuilder);
        verify(notifFactory, times(a)).OreoNotificationFactory(any());
        verify(notifFactory, times(b)).nManagerFactory(any());
    }

}
