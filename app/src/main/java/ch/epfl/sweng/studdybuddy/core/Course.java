package ch.epfl.sweng.studdybuddy.core;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Class representing courses
 */
@Entity
public class Course
{
    @PrimaryKey
    @NonNull
    private ID<Course> courseID; //mustn't be final because it will be reset when we read from the DB #kiru

    private String courseName;

    public Course() {}

    public Course(String courseName)
    {
        this();
        this.courseName = courseName;
        courseID = new ID<>(courseName);
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

    public void setCourseName(String name) { courseName = name; }

    public ID<Course> getCourseID()
    {
        return courseID;
    }

    public void setCourseID(ID<Course> courseID)
    {
        this.courseID = courseID;
    }


}
