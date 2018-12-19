package ch.epfl.sweng.studdybuddy;

import android.os.Build;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.services.notifications.Version;

import static org.junit.Assert.assertEquals;

public class VersionTest {
    Version version = new Version();

    @Test
    public void getRightVersion(){
        assertEquals(Build.VERSION.SDK_INT, version.getBuildVersion());
    }
}
