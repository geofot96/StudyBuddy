package ch.epfl.sweng.studdybuddy;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.services.notifications.ChannelFactory;
import ch.epfl.sweng.studdybuddy.services.notifications.Version;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChannelFactoryTest {
    private Version versionBase = mock(Version.class);
    private ChannelFactory channelFactory = new ChannelFactory();
    @Before
    public void setUp(){
        when(versionBase.getBuildVersion()).thenReturn(Build.VERSION_CODES.BASE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void VersionBase(){
        channelFactory.setVersion(versionBase);
        channelFactory.getNotificationChannel("","",0);
    }

}
