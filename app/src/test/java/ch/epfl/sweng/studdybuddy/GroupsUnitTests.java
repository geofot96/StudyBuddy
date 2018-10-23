package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Unit tests for Group class
 */
public class GroupsUnitTests
{
    private static Course dummy_course = new Course("test");
    private static DummyCourses courses = new DummyCourses();
    private static User user = new User("xxx@yyy.com", "Mr Potato", "IN", new ArrayList<>(), new ArrayList<>());
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

    @Test
    public void compareToWorks()
    {
        Group g1 = new Group(5, new Course("test course"), "en", new ArrayList<>());
        try
        {
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e)
        {

        }
        Group g2 = new Group(5, new Course("test course 2"), "en", new ArrayList<>());
        assertEquals(1, g1.compareTo(g2));
        assertEquals(-1, g2.compareTo(g1));
        assertEquals(0, g1.compareTo(g1));
    }

    @Test
    public void setCreationDateWorks()
    {
        Group g1 = new Group(5, new Course("test course"), "en", new ArrayList<>());
        try
        {
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e)
        {

        }
        Group g2 = new Group(5, new Course("test course 2"), "en", new ArrayList<>());
        assertEquals(1, g1.compareTo(g2));
        try
        {
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e)
        {

        }
        g1.setCreationDate(new SerialDate());
        assertEquals(1, g2.compareTo(g1));
    }

    @Test
    public void testEmptyConstructor()
    {
        Group group = new Group();
    }

    @Test
    public void testSetGroupID()
    {
        Group group = new Group();
        ID<Group> tempID = new ID<>();
        group.setGroupID(tempID.getId());
        assertEquals(tempID.getId(), group.getGroupID());
    }

    /*@Test
    public void testGetParticipants()
    {
        ArrayList<User> list = new ArrayList<>();
        User user1 = new User("1", new ID<>(), null);
        User user2 = new User("2", new ID<>(), null);
        User user3 = new User("3", new ID<>(), null);
        list.add(user1);
        list.add(user2);
        list.add(user3);
        Group group = new Group(5, new Course("test"), "en", list);

        ArrayList<User> result = new ArrayList<>(group.getParticipants());
        assertEquals(result.size(), list.size());
        assertEquals(result.get(0).getUserID(), list.get(0).getUserID());
        assertEquals(result.get(1).getUserID(), list.get(0).getUserID());
        assertEquals(result.get(2).getUserID(), list.get(0).getUserID());
    }*/

    @Test
    public void testGetLang()
    {
        Group group = new Group(5, new Course("test"), "gr", new ArrayList<>());
        assertEquals("gr", group.getLang());
    }

    @Test
    public void testSetLang()
    {
        Group group = new Group(5, new Course("test"), "gr", new ArrayList<>());
        group.setLang("ch");
        assertEquals("ch", group.getLang());
    }
}
