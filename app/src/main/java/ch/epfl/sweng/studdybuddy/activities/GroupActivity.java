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
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.listenDate;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.listenTime;
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
    Group group = new Group();
    List<Meeting> meetings = new ArrayList<>();
    Boolean isAdmin = false;
    Meeting singleton = new Meeting();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent origin = getIntent();
        mm = new MetaMeeting();
        groupID = origin.getStringExtra(Messages.groupID);
        mm.getGroup(groupID, group, updateMeeting());
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
        //setupMeeting();
    }

    private AdapterAdapter updateMeeting() {
        return new AdapterAdapter() {
            @Override
            public void update() {
                setupMeeting();
            }
        };
    }

    public void setDate(View view) {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog  StartTime = new DatePickerDialog(this, listenDate(getMeeting(), group, mm, updateMeeting()), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }


    public void setTime(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, listenTime(getMeeting(), group, mm, updateMeeting()), 0, 0, false);
        timePickerDialog.show();
    }


    public Meeting getMeeting() {
        return singleton;//(meetings.size() > 0) ? meetings.get(0) : new Meeting();
    }
    public void setupMeeting() {
        //meetings.clear();
        Boolean admin = group.getAdminID().equals(userID);
        add.setVisibility(admin ? View.VISIBLE : View.GONE);
        mm.fetchMeetings(group.getGroupID().getId(), meetingConsumer(title, time, date, add));
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
