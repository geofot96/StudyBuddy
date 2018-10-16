package ch.epfl.sweng.studdybuddy;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DummyCourses
{

    //TODO ask if we need this, simple array may be better
    public static final String COMPUTERSCIENCE = "Computer Science";
    public static final String MATHEMATICS = "Mathematics";
    public static final String LIFESCIENCE = "Life Science";
    @StringDef({COMPUTERSCIENCE, MATHEMATICS, LIFESCIENCE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Section{}

    private static final String coursesDB[] = new String[]{"Analysis", "Linear Algebra", "Algorithms", "Functionnal Programming",
            "Computer Language Processing", "Computer Networks"};
    private static final String languagesDB[] = new String[]{"En","Fr","De","It"};

    public static String[] getListOfLanguages()
    {

        return languagesDB;
    }

    public static String[] getListOfCourses()
    {

        return coursesDB;
    }

    public static class MeetingID {
    }

    public static class UserID {
    }

    public static class FriendshipID {
    }
}

