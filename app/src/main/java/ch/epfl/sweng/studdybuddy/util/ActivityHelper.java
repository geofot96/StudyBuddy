package ch.epfl.sweng.studdybuddy.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Intentable;

import static android.app.Activity.RESULT_OK;

public class ActivityHelper {

    public static final Comparator<Meeting> comparator = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return (Long.compare(o1.getStarting(), o2.getStarting()));
        }
    };

    public static View.OnClickListener showDropdown(AutoCompleteTextView tv) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                tv.showDropDown();
            }
        };
    }
   public static android.app.DatePickerDialog.OnDateSetListener listenDate(TextView mDisplayDate, Date startingDate, Date endingDate, AdapterAdapter adapter) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int realMonth = month+1;
                mDisplayDate.setText(realMonth + "/" + dayOfMonth + "/" + year);
                Date tmp = setDate(startingDate, year, month, dayOfMonth);
                startingDate.setTime(tmp.getTime());
                tmp = setDate(endingDate, year, month, dayOfMonth);
                endingDate.setTime(tmp.getTime());
                adapter.update();
            }
        };
    }

    private static Date setDate(Date date, int year, int month, int dayOfMonth){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return c.getTime();
    }

    public static TimePickerDialog.OnTimeSetListener listenTime(TextView mDisplayTime, Date dateToSet, AdapterAdapter adapter) {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.setTime(dateToSet);
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                long tmp = c.getTime().getTime();
                dateToSet.setTime(tmp);
                mDisplayTime.setText(DateTimeHelper.printTime(tmp));
                adapter.update();
            }
        };
    }

    public static Consumer<List<Meeting>> getConsumerForMeetings(List<Meeting> meetingList, MetaMeeting metaM, ID<Group> groupId, RecyclerView.Adapter adapter){

     return new Consumer<List<Meeting>>() {
            @Override
            public void accept(List<Meeting> meetings) {
                meetingList.clear();
                Log.i("DEBUG", Integer.toString(meetings.size()));
                for (Meeting m : meetings) {
                    checkMeeting(m, metaM, groupId, meetingList);
                }
                Collections.sort(meetingList, comparator);
                adapter.notifyDataSetChanged();
            }
        };
    }

    private static void checkMeeting(Meeting m, MetaMeeting metaM, ID<Group> groupId, List<Meeting> meetingList){
        Date currentDate = new Date();
        if (m.getStarting() < currentDate.getTime()) {
            metaM.deleteMeeting(m.getId(), groupId);
        } else {
            meetingList.add(m);
        }
    }

    @Nullable
    public static MeetingLocation meetingLocationFromBundle(int requestCode, int resultCode){
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle data = GlobalBundle.getInstance().getSavedBundle();

            return new MeetingLocation(
                data.getString(Messages.LOCATION_TITLE),
                data.getString(Messages.ADDRESS),
                data.getDouble(Messages.LATITUDE, 0),
                data.getDouble(Messages.LONGITUDE, 0));
        }else{
            return null;
        }
    }

    public static void adminMeeting(Button add, Group group, String userID) {
        Boolean admin = group.getAdminID().equals(userID);
        add.setVisibility(admin ? View.VISIBLE : View.GONE);
    }

    public static View.OnClickListener onClickLaunch(Intentable mother) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mother.launch();
            }
        };
    }

}
