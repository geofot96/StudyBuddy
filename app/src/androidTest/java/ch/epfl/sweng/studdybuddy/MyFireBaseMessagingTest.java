package ch.epfl.sweng.studdybuddy;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
    @Before
    public void setup(){
        remoteMessageBuilder.addData("group_id", Messages.TEST);
        remoteMessageCorrectGroup = remoteMessageBuilder.build();
        remoteMessageBuilder.addData("group_id", "wrongGp");
        remoteMessageWrongGroup = remoteMessageBuilder.build();
        classTested = new MyFirebaseMessaging();
        classTested.setNotifFactory(notifFactory);
        classTested.setCurrentGroupID(Messages.TEST);

        when(notifFactory.nManagerFactory(any())).thenReturn(mockManager);
        when(notifFactory.OreoNotificationFactory(any())).thenReturn(mockOreo);
        when(mockOreo.getNotificationManager()).thenReturn(mockManager);
        when(versionOreo.getBuildVersion()).thenReturn(Build.VERSION_CODES.O);
        when(versionBase.getBuildVersion()).thenReturn(Build.VERSION_CODES.BASE);
        when(mockOreo.getOreoNotification(anyString(), anyString(), any(), any(), anyInt())).thenReturn(notifOreoBuilder);

    }

    @Test
    public void correctlySetUpGroup(){
        assertEquals(Messages.TEST, classTested.getCurrentGroupID());
    }
/*
    @Test
    public void createOreoNotification(){
        classTested.setVersion(versionOreo);
        classTested.onMessageReceived(remoteMessageWrongGroup);
        verify(notifFactory, times(1)).OreoNotificationFactory(any());
        verify(notifFactory, times(0)).nManagerFactory(any());
    }

    @Test
    public void createRegularNotification(){
        classTested.setVersion(versionBase);
        classTested.onMessageReceived(remoteMessageWrongGroup);
        verify(notifFactory, times(0)).OreoNotificationFactory(any());
        verify(notifFactory, times(1)).nManagerFactory(any());
    }

    @Test
    public void noNotificationIfChattingWithTheGroup(){
        classTested.onMessageReceived(remoteMessageCorrectGroup);
        verify(notifFactory, times(0)).OreoNotificationFactory(any());
        verify(notifFactory, times(0)).nManagerFactory(any());
    }
*/
}
