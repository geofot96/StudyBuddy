package ch.epfl.sweng.studdybuddy;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GlobalBundleTest {
  @Test
  public void EmptyTest(){}
  /*
    Bundle bundle = new Bundle();

    @Before
    public void setUp(){
        bundle.putInt(Messages.TEST, 1);
        GlobalBundle.getInstance().putAll(bundle);
    }

    @Test
    public void IsASingleton(){
        GlobalBundle gBundle1 = GlobalBundle.getInstance();
        GlobalBundle gBundle2 = GlobalBundle.getInstance();
        assertEquals(gBundle1, gBundle2);
    }

    @Test
    public void SetTheBundle(){
        Bundle checker = GlobalBundle.getInstance().getSavedBundle();
        assertTrue(checker.getInt(Messages.TEST) == bundle.getInt(Messages.TEST));
    }

    @Test
    public void SetANullBundle(){
        GlobalBundle.getInstance().putAll((Bundle) null);
        Bundle checker = GlobalBundle.getInstance().getSavedBundle();
        assertTrue(checker.getInt(Messages.TEST) == bundle.getInt(Messages.TEST));
    }
*/
}
