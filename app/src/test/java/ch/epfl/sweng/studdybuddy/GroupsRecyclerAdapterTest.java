package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.SerialDate;
import ch.epfl.sweng.studdybuddy.util.GroupsRecyclerAdapter;

import static junit.framework.TestCase.assertEquals;

public class GroupsRecyclerAdapterTest {

    @Test
    public void getCreationDateMethodMonthAndDayLessThenTen() {

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
    public void getCreationDateMethodMonthAndDayMoreThenTen() {

        Course testCourse = new Course("TestCourse");
        Group testGroup = new Group(6, testCourse, "rus", "123", "1231");

        SerialDate serialDate = new SerialDate();
        serialDate.setSeconds(2);
        serialDate.setMinutes(1);
        serialDate.setDate(15);
        serialDate.setMonth(11);
        serialDate.setYear(118);

        testGroup.setCreationDate(serialDate);
        String result = GroupsRecyclerAdapter.getCreationDate(testGroup);
        assertEquals("15-12-2018", result);
    }

    @Test
    public void getCreationDateMethodMonthMoreThenTenAndDayLessThenTen() {

        Course testCourse = new Course("TestCourse");
        Group testGroup = new Group(6, testCourse, "rus", "123", "1231");

        SerialDate serialDate = new SerialDate();
        serialDate.setSeconds(2);
        serialDate.setMinutes(1);
        serialDate.setDate(2);
        serialDate.setMonth(11);
        serialDate.setYear(118);

        testGroup.setCreationDate(serialDate);
        String result = GroupsRecyclerAdapter.getCreationDate(testGroup);
        assertEquals("02-12-2018", result);
    }

    @Test
    public void getCreationDateMethodDayMoreThenTenAndMonthLessThenTen() {

        Course testCourse = new Course("TestCourse");
        Group testGroup = new Group(6, testCourse, "rus", "123", "1231");

        SerialDate serialDate = new SerialDate();
        serialDate.setSeconds(2);
        serialDate.setMinutes(1);
        serialDate.setDate(15);
        serialDate.setMonth(2);
        serialDate.setYear(118);

        testGroup.setCreationDate(serialDate);
        String result = GroupsRecyclerAdapter.getCreationDate(testGroup);
        assertEquals("15-03-2018", result);
    }
}