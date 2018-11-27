package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeetingTest {
    Meeting meeting = new Meeting();
    Date date = new Date();
    @Test
    public void setGetEnding() {
        meeting.setEnding(date.getTime());
        assertTrue(date.getTime() == meeting.getEnding());
    }

    @Test
    public void setGetID() {
        ID<Meeting> abc = new ID<>("abc");
        meeting.setId(abc);
        assertEquals(abc, meeting.getId());
    }

    @Test
    public void setGetStarting() {
        meeting.setStarting(date.getTime());
        assertTrue(date.getTime() == meeting.getStarting());
    }
}
