package ch.epfl.sweng.studdybuddy;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalBundleTest {
    Bundle bundle = mock(Bundle.class);

    @Before
    public void setUp(){
        when(bundle.getInt("test")).thenReturn(1);
    }

    @Test
    public void IsASingleton(){
        GlobalBundle gBundle1 = GlobalBundle.getInstance();
        GlobalBundle gBundle2 = GlobalBundle.getInstance();
        assertEquals(gBundle1, gBundle2);
    }

    @Test
    public void SetTheBundle(){
        GlobalBundle.getInstance().putAll(bundle);
        assertTrue(GlobalBundle.getInstance().getSavedBundle().getInt("test") == 1);
    }

    @Test
    public void SetANullBundle(){
        GlobalBundle.getInstance().putAll(bundle);
        GlobalBundle.getInstance().putAll(null);
        assertTrue(GlobalBundle.getInstance().getSavedBundle().getInt("test") == 1);
    }
}
