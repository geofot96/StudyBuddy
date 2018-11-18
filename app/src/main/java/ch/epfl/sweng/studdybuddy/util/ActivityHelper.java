package ch.epfl.sweng.studdybuddy.util;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Meeting;

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
    public static Consumer<List<Meeting>> meetingConsumer(TextView title, Button time, Button date, Button plus, Boolean isAdmin) {
        return new Consumer<List<Meeting>>() {
            @Override
            public void accept(List<Meeting> meetings) {
                if(meetings.size() == 0) {
                    title.setVisibility(View.GONE);
                    time.setVisibility(View.GONE);
                    date.setVisibility(View.GONE);
                    plus.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
                }
                else {
                    Log.i("UPDATE", meetings.get(0).time());
                    date.setText(meetings.get(0).date());
                    time.setText(meetings.get(0).time());
                    title.setVisibility(View.VISIBLE);
                    time.setVisibility(View.VISIBLE);
                    date.setVisibility(View.VISIBLE);
                    plus.setVisibility(View.GONE);
                }
            }
        };
    }
}
