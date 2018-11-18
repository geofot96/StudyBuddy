package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.core.SerialDate;

import static junit.framework.TestCase.assertEquals;

public class MeetingTest {
    Meeting meeting = new Meeting();
    SerialDate date = new SerialDate();
    @Test
    public void setGetDeadline() {
        meeting.setEnding(date);
        assertEquals(date, meeting.getEnding());
    }

    @Test
    public void setGetID() {
        ID<Meeting> abc = new ID<>("abc");
        meeting.setId(abc);
        assertEquals(abc, meeting.getId());
    }

    @Test
    public void setGetCreation() {
        meeting.setStarting(date);
        assertEquals(date, meeting.getStarting());
    }
}
