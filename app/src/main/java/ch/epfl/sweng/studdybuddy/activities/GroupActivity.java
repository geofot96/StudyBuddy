package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.epfl.sweng.studdybuddy.util.Messages;

public class GroupActivity extends AppCompatActivity {
    String groupID;
    String userID;
    int MaxNumUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent origin = getIntent();
        groupID = origin.getStringExtra(Messages.groupID);
        userID = origin.getStringExtra(Messages.userID);
        MaxNumUsers = origin.getIntExtra(Messages.maxUser, -1);
        goToCalendar();
    }

    private void goToCalendar(){
        Intent intent = new Intent(this, ConnectedCalendarActivity.class){};
        intent.putExtra(Messages.userID, userID);
        intent.putExtra(Messages.groupID, groupID);
        intent.putExtra(Messages.maxUser, MaxNumUsers);
        startActivity(intent);
    }
}
