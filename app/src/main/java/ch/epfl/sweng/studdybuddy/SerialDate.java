package ch.epfl.sweng.studdybuddy;

import java.util.Date;
import java.util.Calendar;

public class SerialDate {

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    int seconds;
    int minutes;
    int day;
    int month;
    int year;
    private final Date d;
    public SerialDate() {
        d  = new Date();
        day = d.getDay();
        year = d.getYear();
        month = d.getMonth();
        minutes = d.getMinutes();
        seconds = d.getSeconds();
    }

    protected Date getDate() {
        return d;
    }

    public boolean before(SerialDate when) {
        return d.before(when.getDate());
    }
    public boolean after(SerialDate when) {
        return d.after(when.getDate());
    }
}
