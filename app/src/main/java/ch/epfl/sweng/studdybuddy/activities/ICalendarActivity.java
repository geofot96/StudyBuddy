package ch.epfl.sweng.studdybuddy.activities;

import android.os.Bundle;
import android.widget.GridLayout;

public interface ICalendarActivity {
    void setOnToggleBehavior(GridLayout calendarGrid);
    public void confirmSlots(Bundle bundle);
    public void updateColor();
}
