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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
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
    int date;
    int month;
    int year;
    private final Date d;
    public SerialDate() {
        d  = new Date();
        date = d.getDate();
        year = d.getYear();
        month = d.getMonth();
        minutes = d.getMinutes();
        seconds = d.getSeconds();
    }

    protected Date getDateElement() {
        return d;
    }

    public boolean before(SerialDate when) {
        return d.before(when.getDateElement());
    }
    public boolean after(SerialDate when) {
        return d.after(when.getDateElement());
    }


}
