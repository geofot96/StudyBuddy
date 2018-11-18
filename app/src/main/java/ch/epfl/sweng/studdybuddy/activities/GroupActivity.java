package ch.epfl.sweng.studdybuddy.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.meetingConsumer;

public class GroupActivity extends AppCompatActivity {
    private String groupID;
    private String userID;
    private int MaxNumUsers;

    private Button calendarButton;
    private Button participantButton;
    Button time;
    Button date;
    Button add;
    TextView title;
    MetaMeeting mm;
    List<Group> group;
    List<Meeting> meetings;
    Boolean isAdmin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent origin = getIntent();
        mm = new MetaMeeting();
        groupID = origin.getStringExtra(Messages.groupID);
        group = new ArrayList<>();
        List<String> id = Arrays.asList(groupID);
        mm.getGroupsfromIds(id, group);
        title = findViewById(R.id.nextMeet);
        userID = origin.getStringExtra(Messages.userID);
        MaxNumUsers = origin.getIntExtra(Messages.maxUser, -1);
        calendarButton = findViewById(R.id.calendarBtn);
        participantButton = findViewById(R.id.participantsBtn);
        time  = findViewById(R.id.time);
        date = findViewById(R.id.date);
        add = findViewById(R.id.addMeeting);
        calendarButton.setOnClickListener(new ClickListener(new Intent(this, ConnectedCalendarActivity.class)));
        participantButton.setOnClickListener(new ClickListener(new Intent(this, GroupInfoActivity.class)));
        setupMeeting();
    }


    public void setDate(View view) {
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog  StartTime = new DatePickerDialog(this, listenDate(getMeeting(), group.get(0), mm), newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }

    public static android.app.DatePickerDialog.OnDateSetListener listenDate(Meeting mee, Group group, MetaMeeting mm) {
        return new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mee.getDeadline().setYear(year);
                mee.getDeadline().setMonth(monthOfYear);
                mm.pushMeeting(mee, group); // new Serial Date
                //setupMeeting();
            }
        };
    }

    public void setTime(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, listenTime(), 0, 0, false);
        timePickerDialog.show();
    }

    public TimePickerDialog.OnTimeSetListener listenTime() {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Meeting mee = getMeeting();
                mee.getDeadline().setMinutes(minute);
                mm.pushMeeting(mee, group.get(0));
                setupMeeting();
            }
        };
    }
    public Meeting getMeeting() {
        return (meetings.size() > 0) ? meetings.get(0) : new Meeting();
    }
    private void setupMeeting() {
        meetings = new ArrayList<>();
        Boolean admin = !group.isEmpty() && group.get(0).getAdminID().equals(userID);
        mm.fetchMeetings(groupID, meetingConsumer(title, time, date, add, true));
    }

    private void goToActivity(Intent intent){
        intent.putExtra(Messages.userID, userID);
        intent.putExtra(Messages.groupID, groupID);
        intent.putExtra(Messages.maxUser, MaxNumUsers);
        startActivity(intent);
    }

    private class ClickListener implements View.OnClickListener {
        private Intent intent;

        public ClickListener(Intent intent){
            this.intent = intent;
        }
        @Override
        public void onClick(View v) {
            goToActivity(intent);
        }
    }
}
