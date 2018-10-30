package ch.epfl.sweng.studdybuddy;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.core.ID;

public class CourseTest {

    @Test
    public void constructorWorks(){
        Course c = new Course("Algebra");
        assert(c.getCourseName().equals("Algebra"));
    }

    @Test
    public void setCourseNameWorks(){
        Course c = new Course("Analysis");
        c.setCourseName("Numerical");
        assert(c.getCourseName().equals("Numerical"));
    }

    @Test
    public void setCourseIDWorks(){
        Course c = new Course("ICC");
        ID<Course> id = new ID<>("0");
        c.setCourseID(id);
        assert(c.getCourseID().equals(id));
    }
}
