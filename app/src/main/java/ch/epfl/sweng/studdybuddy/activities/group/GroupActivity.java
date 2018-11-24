package ch.epfl.sweng.studdybuddy.activities.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class GroupActivity extends AppCompatActivity {
    private Bundle origin;

    private String groupID;
    private String userID;
    private String adminID;
    private int MaxNumUsers;

    private Button calendarButton;
    private Button participantButton;
    private Button createMeetingBtn;
    private Button meetingsBtn;

    private final String TAG = "GROUP_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        GlobalBundle globalBundle = GlobalBundle.getInstance();
        globalBundle.putAll(getIntent().getExtras());
        origin = globalBundle.getSavedBundle();
        groupID = origin.getString(Messages.groupID);
        userID = origin.getString(Messages.userID);
        adminID = origin.getString(Messages.ADMIN);
        MaxNumUsers = origin.getInt(Messages.maxUser, -1);

        if(groupID == null || userID == null || adminID == null || MaxNumUsers == -1){
            startActivity(new Intent(this, NavigationActivity.class));
            Log.d(TAG, "Information of the group is not fully recovered");
        }

        calendarButton = findViewById(R.id.calendarBtn);
        participantButton = findViewById(R.id.participantsBtn);
        createMeetingBtn = findViewById(R.id.createMeeting);
        meetingsBtn = findViewById(R.id.groupMeetingsBtn);

        calendarButton.setOnClickListener(new ClickListener(new Intent(this, ConnectedCalendarActivity.class)));
        participantButton.setOnClickListener(new ClickListener(new Intent(this, GroupInfoActivity.class)));
        createMeetingBtn.setOnClickListener(new ClickListener(new Intent(this, createMeetingActivity.class)));
        meetingsBtn.setOnClickListener(new ClickListener(new Intent(this, MeetingsActivity.class)));
    }


    private void goToActivity(Intent intent){
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
