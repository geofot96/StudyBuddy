package ch.epfl.sweng.studdybuddy.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

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

public class GroupActivity extends AppCompatActivity {
    private String groupID;
    private String userID;
    private int MaxNumUsers;

    private Button calendarButton;
    private Button participantButton;
    TextView time;
    MetaMeeting mm;
    List<Group> group;
    List<Meeting> meetings;
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

        userID = origin.getStringExtra(Messages.userID);
        MaxNumUsers = origin.getIntExtra(Messages.maxUser, -1);
        calendarButton = findViewById(R.id.calendarBtn);
        participantButton = findViewById(R.id.participantsBtn);
        time  = findViewById(R.id.meetTime);
        calendarButton.setOnClickListener(new ClickListener(new Intent(this, ConnectedCalendarActivity.class)));
        participantButton.setOnClickListener(new ClickListener(new Intent(this, GroupInfoActivity.class)));
        setupMeeting();
    }

    public void setMeeting(View view) {
        final Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog  StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mm.pushMeeting(new Meeting(), group.get(0));
                setupMeeting();
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        StartTime.show();
    }

    private void setupMeeting() {
        meetings = new ArrayList<>();
        mm.fetchMeetings(groupID, new Consumer<List<Meeting>>() {
            @Override
            public void accept(List<Meeting> meetings) {
                if(meetings.size() == 0) {
                    TextView meeting = findViewById(R.id.nextMeet);
                    meeting.setVisibility(View.GONE);
                    time.setVisibility(View.GONE);
                }
                else {
                    time.setText(meetings.get(0).getDeadline().toString());
                }
            }
        });

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
