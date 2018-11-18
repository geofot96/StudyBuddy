package ch.epfl.sweng.studdybuddy.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;

public class ActivityHelper {
    public static View.OnClickListener showDropdown(AutoCompleteTextView tv) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                tv.showDropDown();
            }
        };
    }
    public static Consumer<List<Meeting>> meetingConsumer(TextView title, Button time, Button date, Button plus) {
        return new Consumer<List<Meeting>>() {
            @Override
            public void accept(List<Meeting> meetings) {
                if(meetings.size() == 0) {
                    title.setVisibility(View.GONE);
                    time.setVisibility(View.GONE);
                    date.setVisibility(View.GONE);
                }
                else {
                    Log.i("TIME", meetings.get(0).date() + " " + meetings.get(0).time());
                    date.setText(meetings.get(0).date());
                    time.setText(meetings.get(meetings.size()-1).time());
                    title.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                    plus.setVisibility(View.GONE);
                }
            }
        };
    }
    public static android.app.DatePickerDialog.OnDateSetListener listenDate(Meeting mee, Group group, MetaMeeting mm, AdapterAdapter adapter) {
        return new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mee.getDeadline().setYear(year);
                mee.getDeadline().setMonth(monthOfYear);
                mm.pushMeeting(mee, group); // new Serial Date
                adapter.update();
            }
        };
    }
    public static TimePickerDialog.OnTimeSetListener listenTime(Meeting mee, Group g, MetaMeeting mm, AdapterAdapter adapter) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mee.getDeadline().setMinutes(minute);
                mm.pushMeeting(mee, g);
                adapter.update();
            }
        };
    }
}
