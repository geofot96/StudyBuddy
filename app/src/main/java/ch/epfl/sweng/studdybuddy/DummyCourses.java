package ch.epfl.sweng.studdybuddy;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class DummyCourses {
    private ArrayList<Course> listOfCourses= new ArrayList<>();
    public static final String COMPUTERSCIENCE = "Computer Science";
    public static final String MATHEMATICS = "Mathematics";
    public static final String LIFESCIENCE = "Life Science";


    @StringDef({COMPUTERSCIENCE,MATHEMATICS,LIFESCIENCE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Section {}

    public DummyCourses() {
        listOfCourses.add(new Course("Analysis 1","en",COMPUTERSCIENCE));
        listOfCourses.add(new Course("Analysis 1","fr",COMPUTERSCIENCE));
        listOfCourses.add(new Course("Linear Algebra","en",COMPUTERSCIENCE));
        listOfCourses.add(new Course("Linear Algebra","fr",COMPUTERSCIENCE));
        listOfCourses.add(new Course("Physics 1","en",COMPUTERSCIENCE));
        listOfCourses.add(new Course("Physics 1","fr",COMPUTERSCIENCE));
        listOfCourses.add(new Course("Analysis 1","en",MATHEMATICS));
        listOfCourses.add(new Course("Analysis 1","fr",MATHEMATICS));
        listOfCourses.add(new Course("Linear Algebra","en",MATHEMATICS));
        listOfCourses.add(new Course("Linear Algebra","fr",MATHEMATICS));
        listOfCourses.add(new Course("Physics 1","en",MATHEMATICS));
        listOfCourses.add(new Course("Physics 1","fr",MATHEMATICS));
    }

    public ArrayList<Course> getListOfCourses() {

        return listOfCourses;
    }
}
 class Course<X, Y, Z> {
     private final X couseName;
     private final Y language;
     private final Z section;
     public Course(X couseName, Y language, Z section) {
         this.couseName = couseName;
         this.language=language;
         this.section=section;
     }

     public X getCouseName() {
         return couseName;
     }

     public Y getLanguage() {
         return language;
     }

     public Z getSection() {
         return section;
     }
 }
