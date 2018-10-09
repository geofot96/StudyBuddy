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
        Group group = new Group(-5, dummy, participants);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorDoesntAcceptNegMaxParticipants()
    {
        addUsers();
        Group group = new Group(0, dummy, participants);
    }

    @Test
    public void copyConstructorWorksCorrectly()
    {
        Group group = new Group(5, dummy, participants);
        Group group2 = new Group(group);

        assertEquals(group.getParticipantNumber(), group2.getParticipantNumber());
        assertEquals(group.getCourse().getCourseName(), group2.getCourse().getCourseName());
        assertEquals(group.getMaxParticipantNumber(), group2.getMaxParticipantNumber());
    }

    @Test
    public void getParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(4, dummy, participants);
        assertEquals(1, group.getParticipantNumber());
    }

    @Test
    public void getMaxParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(4, dummy, participants);
        assertEquals(4, group.getMaxParticipantNumber());
    }

    @Test
    public void setMaxParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(4, dummy, participants);
        group.setMaxParticipantNumber(5);
        assertEquals(5, group.getMaxParticipantNumber());
    }

    @Test
    public void increaseParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(4, dummy, participants);
        group.increaseParticipantNumber();
        assertEquals(2, group.getParticipantNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void increaseParticipantNumberFailsWhenOverMax()
    {
        addUsers();
        Group group = new Group(3, dummy, participants);
        group.increaseParticipantNumber();
        group.increaseParticipantNumber();
        group.increaseParticipantNumber();
        assertEquals(2, group.getParticipantNumber());
    }

    @Test
    public void getCourseWorks()
    {
        addUsers();
        Group group = new Group(3, dummy, participants);
        assertEquals("test", group.getCourse().getCourseName());
        assertEquals("en", group.getCourse().getLanguage());
        assertEquals("IN", group.getCourse().getSection());
    }

    @Test
    public void setCourseWorks()
    {
        addUsers();
        Group group = new Group(3, dummy, participants);
        Course course = new Course("new course", "fr", "Ma");
        group.setCourse(course);
        assertEquals(group.getCourse().getCourseName(), course.getCourseName());
        assertEquals(group.getCourse().getLanguage(), course.getLanguage());
        assertEquals(group.getCourse().getSection(), course.getSection());
    }

    @Test
    public void setParticipantsWorks()
    {
        addUsers();
        User user2 = new User("2", "Mr Potato 2", "SC", null);
        Group group = new Group(3, dummy, participants);
        ArrayList<User> part = new ArrayList<>(2);
        part.add(user);
        part.add(user2);
        group.setParticipants(part);
        assertEquals(part.get(0).getName(), group.getParticipants().get(0).getName());
        assertEquals(part.get(1).getName(), group.getParticipants().get(1).getName());
    }

    @Test
    public void addParticipantWorks()
    {
        addUsers();
        Group group = new Group(3, dummy, participants);
        User user2 = new User("2", "Mr Potato 2", "SC", null);
        group.addParticipant(user2);
        ArrayList<User> part = group.getParticipants();
        assertEquals(user.getName(), part.get(0).getName());
        assertEquals(user2.getName(), part.get(1).getName());
    }

    @Test
    public void removeParticipantWorks()
    {
        addUsers();
        Group group = new Group(3, dummy, participants);
        User user2 = new User("2", "Mr Potato 2", "SC", null);
        group.addParticipant(user2);
        group.removeParticipant(user2);
        ArrayList<User> part = group.getParticipants();
        assertEquals(user.getName(), part.get(0).getName());
        assertEquals(1, group.getParticipantNumber());
    }


}
