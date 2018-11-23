package ch.epfl.sweng.studdybuddy.util;

import java.util.Date;

public class DateTimeHelper {
    public static String printLongDate(long date){
        return setStringDate("%d/%d/%d", date);
    }

    public static String printShortDate(long date){
        return setStringDate("%d/%d", date);
    }

    private static String setStringDate(String format, long date){
        Date d = new Date(date);
        int day = d.getDate();
        int month = d.getMonth() + 1;
        int year = d.getYear() + 1900;
        return String.format(format,month, day, year);
    }

}
