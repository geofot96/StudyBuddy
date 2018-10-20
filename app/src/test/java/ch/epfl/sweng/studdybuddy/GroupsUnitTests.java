package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for Group class
 */
public class GroupsUnitTests
{
    private static Course dummy_course = new Course("test");
    private static DummyCourses courses = new DummyCourses();
    private static User user = new User( "Mr Potato", new ID<>("dumbid"), new ArrayList<>());
    private ArrayList<User> participants = new ArrayList();

    private void addUsers()
    {
        participants.add(user);
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorDoesntAcceptNegParticipants()
    {
        addUsers();
        Group group = new Group(-5, dummy_course, "fr", participants);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorDoesntAcceptNegMaxParticipants()
    {
        addUsers();
        Group group = new Group(0, dummy_course, "fr", participants);
    }

    @Test
    public void copyConstructorWorksCorrectly()
    {
        Group group = new Group(5, dummy_course, "fr", participants);
        Group group2 = new Group(group);

        assertEquals(group.getParticipantNumber(), group2.getParticipantNumber());
        assertEquals(group.getCourse().getCourseName(), group2.getCourse().getCourseName());
        assertEquals(group.getMaxNoUsers(), group2.getMaxNoUsers());
    }

    @Test
    public void getParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(4, dummy_course, "fr", participants);
        assertEquals(1, group.getParticipantNumber());
    }

    @Test
    public void getMaxParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(4, dummy_course, "fr", participants);
        assertEquals(4, group.getMaxNoUsers());
    }

    @Test
    public void setMaxParticipantNumberWorks()
    {
        addUsers();
        Group group = new Group(4, dummy_course, "fr", participants);
        group.setMaxNoUsers(5);
        assertEquals(5, group.getMaxNoUsers());
    }

    @Test
    public void getCourseWorks()
    {
        addUsers();
        Group group = new Group(3, dummy_course, "fr", participants);
        assertEquals("test", group.getCourse().getCourseName());
        //TODO check for the uid
    }

    @Test
    public void setCourseWorks()
    {
        addUsers();
        Group group = new Group(3, dummy_course, "fr", participants);
        Course course = new Course("new course");
        group.setCourse(course);
        assertEquals(group.getCourse().getCourseName(), course.getCourseName());
        //TODO check for the uid
    }

}
