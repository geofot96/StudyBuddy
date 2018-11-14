package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.SerialDate;

import static junit.framework.TestCase.assertEquals;

public class MeetingTest {
    Meeting meeting = new Meeting();
    SerialDate date = new SerialDate();
    @Test
    public void setGetDate() {
        meeting.setDeadline(date);
        assertEquals(date, meeting.getDeadline());
    }

    @Test
    public void setGetID() {
        ID<Meeting> abc = new ID<>("abc");
        meeting.setId(abc);
        assertEquals(abc, meeting.getId());
    }
}
