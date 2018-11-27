package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import ch.epfl.sweng.studdybuddy.util.DateTimeHelper;

import static org.junit.Assert.assertEquals;

public class DateTimeHelperTest {
    DateTimeHelper DTH = new DateTimeHelper();
    Date date = new Date();
    Calendar c = Calendar.getInstance();
    @Test
    public void longDate(){
        c.setTime(date);
        String check = String.format("%d/%d/%d", c.get(Calendar.MONTH) +1, c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.YEAR));
        assertEquals(check, DTH.printLongDate(date.getTime()));
    }

    @Test
    public void meetingDateTest(){
        c.setTime(date);
        String check = String.format(
                "%d/%d From: %d:%d%d To: %d:%d%d",
                c.get(Calendar.MONTH) +1,
                c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE)/10,
                c.get(Calendar.MINUTE)%10,
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE)/10,
                c.get(Calendar.MINUTE)%10);
        assertEquals(check, DTH.printMeetingDate(date.getTime(), date.getTime()));
    }
}
