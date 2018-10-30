package ch.epfl.sweng.studdybuddy;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.SerialDate;

public class SerialDateTest {
    public SerialDate date;
    @Before
    public void setup() {
        date = new SerialDate();
    }
    /*@Test
    public void beforeAfterWorks(){
        SerialDate after = new SerialDate();
        assert(date.before(after));
        assert(after.after(date));
    }*/

    @Test
    public void setWorks(){
        SerialDate d = new SerialDate();
        d.setDay(10);
        d.setSeconds(10);
        d.setMinutes(10);
        d.setMonth(10);
        d.setYear(2018);
        assert(d.getDay() == 10 && d.getYear() == 2018 && d.getSeconds()==10 && d.getMinutes()==10 &&d.getMonth()==10);
    }

}
