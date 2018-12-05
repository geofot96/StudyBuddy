package ch.epfl.sweng.studdybuddy.util;

import java.util.Calendar;
import java.util.Date;

public class DateTimeHelper {
    public static String printLongDate(long date){
        return setStringDate("%d/%d/%d", date);
    }

    public static String printMeetingDate(long startingDate, long endingDate){
        return String.format("%s From: %s To: %s", setStringDate("%d/%d", startingDate), setStringTime(startingDate), setStringTime(endingDate));
    }

    public static String printTime(long time){
        return setStringTime(time);
    }

    private static String setStringDate(String format, long date){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        return String.format(format,month, day, year);
    }

    private static String setStringTime(long date){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return String.format("%d:%d%d", hour, minute/10, minute%10);
    }
}
