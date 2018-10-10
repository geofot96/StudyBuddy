package ch.epfl.sweng.studdybuddy;

/**
 * Class representing courses
 */
import java.util.UUID;
public class Course
{
    private final String courseName;
    private UUID courseID; //mustn't be final because it will be reset when we read from the DB #kiru

    public Course(String courseName)
    {
        this.courseName = courseName;
        courseID = UUID.randomUUID();
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

    public UUID getCourseID()
    {
        return courseID;
    }

    public void setCourseID(UUID courseID)
    {
        this.courseID = courseID;
    }
}
