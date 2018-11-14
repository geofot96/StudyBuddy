package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.util.GroupsRecyclerAdapter;

import static junit.framework.TestCase.assertEquals;

public class GroupsRecyclerAdapterTest {

    @Test
    public void getCreationDateMethodGetRightFormatOfDateFromGroup(){

        Course testCourse = new Course("TestCourse");
        Group testGroup = new Group(6, testCourse, "rus", "123", "1231");
        String date = GroupsRecyclerAdapter.getCreationDate(testGroup);

        Date targetDate = new Date();
        int day = targetDate.getDate();
        int month = targetDate.getMonth();
        int year = targetDate.getYear();

        String target = String.valueOf(day) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year + 1900);


        assertEquals(target, date);
    }
}