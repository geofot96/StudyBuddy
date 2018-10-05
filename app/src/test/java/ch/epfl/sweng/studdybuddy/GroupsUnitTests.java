package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Unit tests for Group class
 */
public class GroupsUnitTests
{
    private static Course dummy = new Course("test", "en", "IN");
    private static DummyCourses courses = new DummyCourses();
    private static User user = new User("1", "Mr Potato", "IN", null);
    private ArrayList<User> participants = new ArrayList();

    private void addUsers()
    {
        participants.add(user);
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorDoesntAcceptNegParticipants()
    {
        addUsers();
        Group group = new Group(-1, 5, dummy, participants);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorDoesntAcceptNegMaxParticipants()
    {
        addUsers();
        Group group = new Group(1, 0, dummy, participants);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorDoesntAcceptMaxParticipantsToBeLessThan()
    {
        addUsers();
        Group group = new Group(4, 2, dummy, participants);
    }

    @Test
    public void getParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(1, 4, dummy, participants);
        assertEquals(1, group.getParticipantNumber());
    }

    @Test
    public void getMaxParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(1, 4, dummy, participants);
        assertEquals(4, group.getMaxParticipantNumber());
    }

    @Test
    public void setMaxParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(1, 4, dummy, participants);
        group.setMaxParticipantNumber(5);
        assertEquals(5, group.getMaxParticipantNumber());
    }

    @Test
    public void increaseParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(1, 4, dummy, participants);
        group.increaseParticipantNumber();
        assertEquals(2, group.getParticipantNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void increaseParticipantNumberFailsWhenOverMax()
    {
        addUsers();
        Group group = new Group(1, 3, dummy, participants);
        group.increaseParticipantNumber();
        group.increaseParticipantNumber();
        group.increaseParticipantNumber();
        assertEquals(2, group.getParticipantNumber());
    }

    @Test
    public void getCourseWorks()
    {
        addUsers();
        Group group = new Group(1, 3, dummy, participants);
        assertEquals("test", group.getCourse().getCourseName());
        assertEquals("fr", group.getCourse().getLanguage());
        assertEquals("IN", group.getCourse().getSection());
    }

    @Test
    public void addParticipantWorks()
    {
        addUsers();
        Group group = new Group(1, 3, dummy, participants);
        User user2 = new User("2", "Mr Potato 2", "SC", null);
        group.addParticipant(user2);
        group.showParticipants(); //TODO check with get participants
    }


}
