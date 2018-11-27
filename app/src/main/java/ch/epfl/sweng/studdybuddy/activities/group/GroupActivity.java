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
    private boolean wrongInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        GlobalBundle globalBundle = GlobalBundle.getInstance();
        globalBundle.putAll(getIntent().getExtras());
        Bundle origin = globalBundle.getSavedBundle();
        String groupID = origin.getString(Messages.groupID);
        String userID = origin.getString(Messages.userID);
        String adminID = origin.getString(Messages.ADMIN);
        int maxNumUsers = origin.getInt(Messages.maxUser, -1);

        if(groupID == null || userID == null || adminID == null || maxNumUsers == -1){
            String TAG = "GROUP_ACTIVITY";
            Log.d(TAG, "Information of the group is not fully recovered");
            wrongInput = true;
            startActivity(new Intent(this, NavigationActivity.class));
        }

        Button calendarButton = findViewById(R.id.calendarBtn);
        Button participantButton = findViewById(R.id.participantsBtn);
        Button createMeetingBtn = findViewById(R.id.createMeeting);
        Button meetingsBtn = findViewById(R.id.groupMeetingsBtn);

        calendarButton.setOnClickListener(new ClickListener(new Intent(this, ConnectedCalendarActivity.class)));
        participantButton.setOnClickListener(new ClickListener(new Intent(this, GroupInfoActivity.class)));
        createMeetingBtn.setOnClickListener(new ClickListener(new Intent(this, createMeetingActivity.class)));
        meetingsBtn.setOnClickListener(new ClickListener(new Intent(this, MeetingsActivity.class)));
        createMeetingBtn.setEnabled(userID.equals(adminID));
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

    public boolean getInfoWrongInput(){
        return wrongInput;
    }
}
