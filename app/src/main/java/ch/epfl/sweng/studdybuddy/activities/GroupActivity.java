package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class GroupActivity extends AppCompatActivity {
    private String groupID;
    private String userID;
    private int MaxNumUsers;

    private Button calendarButton;
    private Button participantButton;
    private Button mapsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent origin = getIntent();
        groupID = origin.getStringExtra(Messages.groupID);
        userID = origin.getStringExtra(Messages.userID);
        MaxNumUsers = origin.getIntExtra(Messages.maxUser, -1);
        calendarButton = findViewById(R.id.calendarBtn);
        participantButton = findViewById(R.id.participantsBtn);
        mapsButton = findViewById(R.id.mapsButton);
        calendarButton.setOnClickListener(new ClickListener(new Intent(this, ConnectedCalendarActivity.class)));
        participantButton.setOnClickListener(new ClickListener(new Intent(this, GroupInfoActivity.class)));
        mapsButton.setOnClickListener(new ClickListener(new Intent(this, MapsActivity.class)));
    }

    private void goToActivity(Intent intent){
        intent.putExtra(Messages.userID, userID);
        intent.putExtra(Messages.groupID, groupID);
        intent.putExtra(Messages.maxUser, MaxNumUsers);
        intent.putExtra(Messages.meetingID, "1");

        //Just for testing
        FirebaseReference ref = new FirebaseReference();
       ;
        Meeting m1, m2 ,m3 ;
        m1 = new Meeting("1");
        m2 = new Meeting("2");
        m3 = new Meeting("3");

        List<Meeting> meetings = Arrays.asList(m1,m2,m3);
        for(Meeting m: meetings){
            m.setLocation(MapsHelper.ROLEX_LOCATION);
        }
        ref.select("BoubaMeetings").setVal(meetings);
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
