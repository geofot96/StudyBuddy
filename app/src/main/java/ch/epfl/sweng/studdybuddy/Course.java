package ch.epfl.sweng.studdybuddy;

/**
 * Class representing courses
 */

public class Course
{
    private final String courseName;
    private final int courseID;

    public Course(String courseName, int courseID)
    {
        this.courseName = courseName;
        this.courseID = courseID;
    }

    public Course(Course sourceCourse)
    {
        this.courseName = sourceCourse.getCourseName();
        this.courseID = sourceCourse.getCourseID();
    }

    public String getCourseName()
    {
        return  courseName;
    }

    public int getCourseID()
    {
        return courseID;
    }



}
