package ch.epfl.sweng.studdybuddy.core;

import java.util.Date;

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

    public int getHour(){return hour;}
    public void setHour(int hour){this.hour = hour;}

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

    private int seconds;
    private int minutes;
    private int hour;
    private int day;
    private int month;
    private int year;
    private final Date d;

    public SerialDate() {
        d  = new Date();
        year = d.getYear();
        month = d.getMonth()+1;
        day = d.getDay();
        hour = d.getHours();
        minutes = d.getMinutes();
        seconds = d.getSeconds();
    }

    public SerialDate(int y, int m, int d, int h, int min ,int sec) {
        day = d;
        year = y;
        month = m;
        hour = h;
        minutes = min;
        seconds = sec;
        this.d = new Date();
    }

    protected Date getDate() {
        return d;
    }


    public boolean before(SerialDate when) {
        return this.d.before(when.getDate());
    }
    public boolean after(SerialDate when) {
        return d.after(when.getDate());
    }


}
