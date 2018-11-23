package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.activities.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class GroupActivity extends AppCompatActivity {
    private String groupID;
    private String userID;
    private String adminID;
    private int MaxNumUsers;

    private Button calendarButton;
    private Button participantButton;
    private Button createMeetingBtn;
    private Button meetingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent origin = getIntent();
        groupID = origin.getStringExtra(Messages.groupID);
        userID = origin.getStringExtra(Messages.userID);
        adminID = origin.getStringExtra(Messages.ADMIN);
        MaxNumUsers = origin.getIntExtra(Messages.maxUser, -1);
        if(groupID == null || userID == null || MaxNumUsers == -1){
            startActivity(new Intent(this, GroupActivity.class));
        }
        calendarButton = findViewById(R.id.calendarBtn);
        participantButton = findViewById(R.id.participantsBtn);
        createMeetingBtn = findViewById(R.id.createMeeting);
        meetingsBtn = findViewById(R.id.groupMeetingsBtn);

        calendarButton.setOnClickListener(new ClickListener(new Intent(this, ConnectedCalendarActivity.class)));
        participantButton.setOnClickListener(new ClickListener(new Intent(this, GroupInfoActivity.class)));
        createMeetingBtn.setOnClickListener(new ClickListener(new Intent(this, createMeetingActivity.class)));
        meetingsBtn.setOnClickListener(new ClickListener(new Intent(this, MeetingsActivity.class)));

        createMeetingBtn.setEnabled(userID.equals(adminID));
    }

    private void goToActivity(Intent intent){
        intent.putExtra(Messages.userID, userID);
        intent.putExtra(Messages.groupID, groupID);
        intent.putExtra(Messages.maxUser, MaxNumUsers);
        intent.putExtra(Messages.ADMIN, adminID);
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
