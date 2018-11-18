package ch.epfl.sweng.studdybuddy.core;

import java.util.Date;

public class SerialDate
{
    int seconds;
    int minutes;
    int day;
    int month;
    int year;
    private final Date d;

    public SerialDate()
    {
        d = new Date();
        day = d.getDay();
        year = d.getYear();
        month = d.getMonth();
        minutes = d.getMinutes();
        seconds = d.getSeconds();
    }

    public int getSeconds()
    {
        return seconds;
    }

    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public void setMinutes(int minutes)
    {
        this.minutes = minutes;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public int getMonth()
    {
        return 1 + month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getYear()
    {
        return 1900 + year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }


    protected Date getDate()
    {
        return d;
    }

    public boolean before(SerialDate when)
    {
        return this.d.before(when.getDate());
    }

    public boolean after(SerialDate when)
    {
        return this.d.after(when.getDate());
    }

    @Override
    public String toString()
    {
        return getDay() + "-" + getMonth() + "-" + getYear();
    }
}
