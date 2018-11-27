package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.MeetingLocation;
import ch.epfl.sweng.studdybuddy.core.SerialDate;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MeetingTest {
    Meeting meeting = new Meeting();
    SerialDate date = new SerialDate();
    @Test
    public void setGetDeadline() {
        meeting.setDeadline(date);
        assertEquals(date, meeting.getDeadline());
    }

    @Test
    public void setGetID() {
        ID<Meeting> abc = new ID<>("abc");
        meeting.setId(abc);
        assertEquals(abc, meeting.getId());
    }

    @Test
    public void setGetCreation() {
        meeting.setCreation(date);
        assertEquals(date, meeting.getCreation());
    }

    @Test
    public void copyTest(){
        Meeting b = new Meeting();
        b.copy(meeting);
        assertTrue(b.creation.equals(meeting.creation)&& b.getDeadline().equals(meeting.getDeadline())
                && b.getId().equals(meeting.getId()) && b.getLocation().equals(meeting.getLocation()));
    }

    @Test
    public void setGetLocationTest(){
        MeetingLocation location = new MeetingLocation("Guinée", "Conakry",0,0);
        meeting.setLocation(location );
        assertTrue(location.equals(meeting.getLocation()));
    }
}
