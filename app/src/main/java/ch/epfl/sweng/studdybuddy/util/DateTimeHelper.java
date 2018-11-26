package ch.epfl.sweng.studdybuddy.util;

import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {
    public static String printLongDate(long date){
        return setStringDate("%d/%d/%d", date);
    }

    private static String setStringDate(String format, long date){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        return String.format(format,month, day, year);
    }

}
