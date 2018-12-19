package ch.epfl.sweng.studdybuddy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.calendar.CalendarComputation;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Calendar class
 */

public class CalendarUnitTests {
    private CalendarComputation calendarController;
    @Before
    public void setUp(){
        calendarController = new CalendarComputation();
    }

    @Test
    public void twoZeroIntegerListReturnZeroIntegerList(){

        List<Integer> a = new ArrayList<>();
        pushNValue(a, 0, 5);
        List<Integer> b = new ArrayList<>();
        pushNValue(b, 0, 5);
        List<Integer> c = new ArrayList<>();
        pushNValue(c, 0, 5);

        List<Integer> result =  calendarController.getSumOfTwoLists(a,b);
        assertEquals(c, result);
    }

    private static void pushNValue(List<Integer> list, Integer value, Integer N) {
        for(int i = 0; i < N; ++i) {
            list.add(value);
        }
    }
    @Test
    public void twoOneFilledIntegerListReturnTwoFilledIntegerList(){

        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<Integer> c = new ArrayList<>();
        pushNValue(a, 1, 5);
        pushNValue(b, 1, 5);
        pushNValue(c, 2, 5);

        List<Integer> result =  calendarController.getSumOfTwoLists(a,b);
        assertEquals(c, result);
    }


    @Test
    public void falseBooleanListEqualZeroIntegerList(){

        List<Boolean> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        for (int i = 0; i < 5; i ++){
            a.add(Boolean.FALSE);
        }
        pushNValue(b, 0, 5);


        List<Integer> result =  calendarController.getIntegerListFromBooleanList(a);
        assertEquals(b, result);
    }

    @Test
    public void trueBooleanListEqualOneFilledIntegerList(){

        List<Integer> b = new ArrayList<>();
        List<Boolean> a = new ArrayList<>();
        pushNValue(b, 1, 5);
        for (int i = 0; i < 5; i ++){
            a.add(Boolean.TRUE);
        }

        List<Integer> result =  calendarController.getIntegerListFromBooleanList(a);
        assertEquals(b, result);
    }

    @Test
    public void sumFewListsReturnCertainAnswer(){

        List<Boolean> a = new ArrayList<>();
        List<Boolean> b = new ArrayList<>();
        List<Boolean> c = new ArrayList<>();
        List<Integer> d = new ArrayList<>();
        pushNValue(d, 2, 5);
        for (int i = 0; i < 5; i ++){
            a.add(Boolean.TRUE);
            b.add(Boolean.TRUE);
            c.add(Boolean.FALSE);
        }

        List<List<Boolean>> lists = new ArrayList<>();

        lists.add(a);
        lists.add(b);
        lists.add(c);

        List<Integer> result =  calendarController.sumBooleanLists(lists);
        assertEquals(d, result);
    }
}
