package ch.epfl.sweng.studdybuddy;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.SerialDate;
import org.junit.Assert.*;

public class SerialDateTest {
    public SerialDate date;
    @Before
    public void setup() {
        date = new SerialDate();
    }

    @Test
    public void setWorks(){
        SerialDate d = new SerialDate();
        d.setDay(10);
        d.setSeconds(10);
        d.setMinutes(10);
        d.setMonth(9);
        d.setYear(118);
        assert(d.getDay() == 10 && d.getYear() == 2018 && d.getSeconds()==10 && d.getMinutes()==10 &&d.getMonth()==10);
    }

    @Test
    public void testToString()
    {
        SerialDate d = new SerialDate();
        d.setDay(10);
        d.setMonth(9);
        d.setYear(118);
        String actual = "10-10-2018";
        assert(actual.equals(d.toString()));
    }

    @Test
    public void testBeforeAfter()
    {
        SerialDate d1 = new SerialDate();
        try
        {
            Thread.sleep(800);
        }
        catch(Exception e){}
        SerialDate d2 = new SerialDate();
        assert(d1.before(d2));
        assert(d2.after(d1));
    }

}
