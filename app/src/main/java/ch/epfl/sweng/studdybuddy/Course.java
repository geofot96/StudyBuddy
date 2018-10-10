package ch.epfl.sweng.studdybuddy;

/**
 * Class representing courses
 */
import java.util.UUID;
public class Course
{
    private final String courseName;
   // private final int courseID;

    public Course(String courseName)
    {
        this.courseName = courseName;

    }

    public Course(Course sourceCourse)
    {
        this.courseName = sourceCourse.getCourseName();

    }

    public String getCourseName()
    {
        return  courseName;
    }

//    public int getCourseID()
//    {
//        return courseID;
//    }



}
