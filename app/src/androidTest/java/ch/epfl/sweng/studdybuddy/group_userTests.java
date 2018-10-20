package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static ch.epfl.sweng.studdybuddy.DummyCourses.MATHEMATICS;

public class group_userTests
{


    @Test
    public void canAddUser()
    {
        Group g1 = new Group(5, new Course("test"), "fr", new ArrayList<>());
        User u1 = new User( "Mr Potato", new ID<>("dumbid"), new ArrayList<>());
        g1.addParticipant(u1);
        assertEquals(g1.getParticipants().get(0), u1);
    }
}
