package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for Calendar class
 */

public class CalendarUnitTests {

    @Test
    public void twoZeroIntegerListReturnZeroIntegerList(){

        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<Integer> c = new ArrayList<>();
        for (int i = 0; i < 5; i ++){
            a.add(0);
            b.add(0);
            c.add(0);
        }

        Calendar testCalendar = new Calendar();
        List<Integer> result =  testCalendar.getSumOfTwoLists(a,b);
        assertEquals(c, result);
    }

    @Test
    public void twoOneFilledIntegerListReturnTwoFilledIntegerList(){

        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<Integer> c = new ArrayList<>();
        for (int i = 0; i < 5; i ++){
            a.add(1);
            b.add(1);
            c.add(2);
        }

        Calendar testCalendar = new Calendar();
        List<Integer> result =  testCalendar.getSumOfTwoLists(a,b);
        assertEquals(c, result);
    }
}
