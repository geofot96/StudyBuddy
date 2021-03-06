package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

    @Test
    public void setGetLocationTest(){
        MeetingLocation location = new MeetingLocation("Guinée", "Conakry",0,0);
        meeting.setLocation(location);
        assertTrue(location.equals(meeting.getLocation()));
    }

    @Test
    public void constructMeetingWithRandomIDAndDefaultLocation(){
        Meeting withDefaultLocation = new Meeting(date.getTime(), date.getTime());
        MeetingLocation emptyLocation = new MeetingLocation();
        assertTrue(emptyLocation.equals(withDefaultLocation.getLocation()));
        assertNotEquals(UUID.randomUUID().toString(), withDefaultLocation.getId().getId());
    }
}
