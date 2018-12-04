package ch.epfl.sweng.studdybuddy;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GlobalBundleTest {

    Bundle bundle = new Bundle();
    Meeting m;

    @Before
    public void setUp() {
        bundle.putInt(Messages.TEST, 1);
        GlobalBundle.getInstance().putAll(bundle);
        String s = Messages.TEST;
        m = new Meeting((long) 0, (long) 0, new MeetingLocation(s, s, 0, 0), s);
    }

    @Test
    public void IsASingleton() {
        GlobalBundle gBundle1 = GlobalBundle.getInstance();
        GlobalBundle gBundle2 = GlobalBundle.getInstance();
        assertEquals(gBundle1, gBundle2);
    }

    @Test
    public void SetTheBundle() {
        Bundle checker = GlobalBundle.getInstance().getSavedBundle();
        assertTrue(checker.getInt(Messages.TEST) == bundle.getInt(Messages.TEST));
    }

    @Test
    public void SetANullBundle() {
        GlobalBundle.getInstance().putAll((Bundle) null);
        Bundle checker = GlobalBundle.getInstance().getSavedBundle();
        assertTrue(checker.getInt(Messages.TEST) == bundle.getInt(Messages.TEST));
    }

    @Test
    public void pushMeeting() {
        GlobalBundle.getInstance().putMeeting(m);
        testingMeeting();
    }

    @Test
    public void putNullDoesNothing() {
        GlobalBundle.getInstance().putMeeting(m);
        GlobalBundle.getInstance().putMeeting(null);
        testingMeeting();
    }

    @Test
    public void getMeeting(){
        GlobalBundle.getInstance().putMeeting(m);
        Meeting toCheck = GlobalBundle.getInstance().getMeeting();
        assertEquals((long) 0, toCheck.getStarting());
        assertEquals((long) 0, toCheck.getEnding());
        assertTrue(toCheck.getLocation().getLatitude() == 0);
        assertTrue(toCheck.getLocation().getLongitude() == 0);
        assertEquals(Messages.TEST, toCheck.getId().getId());
        assertEquals(Messages.TEST, toCheck.getLocation().getTitle());
        assertEquals(Messages.TEST, toCheck.getLocation().getAddress());
    }

    private void testingMeeting(){
        Bundle b = GlobalBundle.getInstance().getSavedBundle();
        assertEquals((long) 0, b.getLong(Messages.M_SDATE));
        assertEquals((long) 0, b.getLong(Messages.M_EDATE));
        assertTrue(b.getDouble(Messages.LATITUDE) == 0);
        assertTrue(b.getDouble(Messages.LONGITUDE) == 0);
        assertEquals(Messages.TEST, b.getString(Messages.meetingID));
        assertEquals(Messages.TEST, b.getString(Messages.LOCATION_TITLE));
        assertEquals(Messages.TEST, b.getString(Messages.ADDRESS));
    }
}
