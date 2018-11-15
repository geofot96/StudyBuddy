package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.SerialDate;
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

    @Test
    public void getCreationDateMethodGetRightFormatOfDateBeginningOfMonth() {

        Course testCourse = new Course("TestCourse");
        Group testGroup = new Group(6, testCourse, "rus", "123", "1231");

        SerialDate serialDate = new SerialDate();
        serialDate.setSeconds(2);
        serialDate.setMinutes(1);
        serialDate.setDate(2);
        serialDate.setMonth(2);
        serialDate.setYear(118);

        testGroup.setCreationDate(serialDate);
        String result = GroupsRecyclerAdapter.getCreationDate(testGroup);
        assertEquals("02-03-2018", result);
    }

    @Test
    public void getCreationDateMethodGetRightFormatOfDateBeginningOfMonthAndMothInTheEndOfTheYear() {

        Course testCourse = new Course("TestCourse");
        Group testGroup = new Group(6, testCourse, "rus", "123", "1231");

        SerialDate serialDate = new SerialDate();
        serialDate.setSeconds(2);
        serialDate.setMinutes(1);
        serialDate.setDate(2);
        serialDate.setMonth(10);
        serialDate.setYear(118);

        testGroup.setCreationDate(serialDate);
        String result = GroupsRecyclerAdapter.getCreationDate(testGroup);
        assertEquals("02-11-2018", result);
    }
}