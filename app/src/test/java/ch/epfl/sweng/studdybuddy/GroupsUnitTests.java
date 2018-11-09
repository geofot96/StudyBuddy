package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;

import static ch.epfl.sweng.studdybuddy.core.Group.groupOf;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Group class
 */
public class GroupsUnitTests
{
    private static User user = new User( "Mr Potato", new ID<>("dumbid"));
    private ArrayList<User> participants = new ArrayList();

    private void addUsers()
    {
        participants.add(user);
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructorDoesntAcceptNegParticipants()
    {
        addUsers();
        Group group = groupOf(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorDoesntAcceptNegMaxParticipants() {
        addUsers();
        Group group = groupOf(0);
    }

    @Test
    public void getMaxParticipantNumberWorks()
    {
        addUsers();
        Group group = groupOf(4);
        assertEquals(4, group.getMaxNoUsers());
    }

    @Test
    public void setMaxParticipantNumberWorks()
    {
        addUsers();
        Group group = groupOf(4);
        group.setMaxNoUsers(5);
        assertEquals(5, group.getMaxNoUsers());
    }

    @Test
    public void getCourseWorks()
    {
        addUsers();
        Group group = groupOf(3);
        assertEquals("test", group.getCourse().getCourseName());
        //TODO check for the uid
    }

    @Test
    public void setCourseWorks()
    {
        addUsers();
        Group group = groupOf(3);
        Course course = new Course("new course");
        group.setCourse(course);
        assertEquals(group.getCourse().getCourseName(), course.getCourseName());
        //TODO check for the uid
    }




}
