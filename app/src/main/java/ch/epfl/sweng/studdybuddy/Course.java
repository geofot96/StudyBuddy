package ch.epfl.sweng.studdybuddy;

/**
 * Class representing courses
 */
//TODO add a semester attribute

public class Course
{
    private final String courseName;
    private final String language;
    private final String section;

    public Course(String courseName, String language, String section)
    {
        this.courseName = courseName;
        this.language = language;
        this.section = section;
    }

    public Course(Course sourceCourse)
    {
        this.courseName=sourceCourse.getCourseName();
        this.language=sourceCourse.getLanguage();
        this.section=sourceCourse.getSection();
    }

    public String getCourseName()
    {
        return  courseName;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getSection()
    {
        return section;
    }


}
