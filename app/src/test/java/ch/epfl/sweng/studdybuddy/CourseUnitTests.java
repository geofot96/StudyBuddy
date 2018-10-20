package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import static org.junit.Assert.*;

public class CourseUnitTests
{
    @Test
    public void testDefaultConstructor()
    {
        Course course = new Course();
    }


    @Test
    public void testGetCourseName()
    {
        Course course = new Course("testCourse");
        assertEquals("testCourse", course.getCourseName());

    }

    @Test
    public void testSetCourseName()
    {
        Course course = new Course("testCourse");
        course.setCourseName("new_name");
        assertEquals("new_name", course.getCourseName());
    }

    @Test
    public void testSetCourseID()
    {
        Course course = new Course("testCourse");
        ID<Course> tempID = new ID<>();
        course.setCourseID(tempID);
        assertEquals(tempID.getId(), course.getCourseID().getId());
    }
}
