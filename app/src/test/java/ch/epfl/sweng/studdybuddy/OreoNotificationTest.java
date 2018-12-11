package ch.epfl.sweng.studdybuddy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import ch.epfl.sweng.studdybuddy.services.notifications.ChannelFactory;
import ch.epfl.sweng.studdybuddy.services.notifications.NotifFactory;
import ch.epfl.sweng.studdybuddy.services.notifications.OreoNotification;
import ch.epfl.sweng.studdybuddy.services.notifications.Version;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OreoNotificationTest {

    private Version versionOreo = mock(Version.class);
    private Version versionBase = mock(Version.class);
    private ChannelFactory channelFactory = mock(ChannelFactory.class);
    private NotificationChannel notificationChannel = mock(NotificationChannel.class);
    private NotifFactory notifFactory = mock(NotifFactory.class);
    private NotificationManager notificationManager = mock(NotificationManager.class);
    private Context context = mock(Context.class);

    @Before
    public void setUp(){
        when(channelFactory.getNotificationChannel(anyString(),any(),anyInt())).thenReturn(notificationChannel);
        when(notifFactory.nManagerFactory(any())).thenReturn(notificationManager);
        when(versionOreo.getBuildVersion()).thenReturn(Build.VERSION_CODES.O);
        when(versionBase.getBuildVersion()).thenReturn(Build.VERSION_CODES.BASE);
        OreoNotification.setChannelFactory(channelFactory);
        OreoNotification.setNotifFactory(notifFactory);
    }

    @Test
    public void createOreoNotificationVersionOreo(){
        OreoNotification.setVersion(versionOreo);
        new OreoNotification(context);
        verify(channelFactory, times(1)).getNotificationChannel(anyString(), any(), anyInt());
        verify(notificationManager, times(1)).createNotificationChannel(notificationChannel);
    }

    @Test
    public void createRegularNotification(){
        OreoNotification.setVersion(versionBase);
        new OreoNotification(context);
        verify(channelFactory, times(0)).getNotificationChannel(anyString(), any(), anyInt());
        verify(notificationManager, times(0)).createNotificationChannel(notificationChannel);
    }
}
