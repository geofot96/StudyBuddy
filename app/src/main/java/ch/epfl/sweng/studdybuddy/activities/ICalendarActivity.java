package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.view.View;
import android.widget.GridLayout;

public interface ICalendarActivity {
    void setOnToggleBehavior(GridLayout calendarGrid);
    public void confirmSlots(Intent intent);
    public void updateColor();
}
