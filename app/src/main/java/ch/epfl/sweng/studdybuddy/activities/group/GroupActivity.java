package ch.epfl.sweng.studdybuddy.activities.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

public class GroupActivity extends AppCompatActivity {
    private boolean wrongInput = false;
    List<User> participants  = new ArrayList<>();
    MetaGroupAdmin mb  = new MetaGroupAdmin();
    private String uId;
    private String gId;
    Button button;
    List<Group> group = new ArrayList<>();
    List<String> gIds = new ArrayList<>();

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
        uId = ((StudyBuddy) GroupActivity.this.getApplication()).getAuthendifiedUser().getUserID().getId();
        gId = origin.getString(Messages.groupID);
        gIds.add(gId);
        mb.getGroupsfromIds(gIds, group);
        mb.getGroupUsers(gId, participants);
        setUI();

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

    public void setUI(){
        ParticipantAdapter participantAdapter = new ParticipantAdapter(participants);
        mb.addListenner(new RecyclerAdapterAdapter(participantAdapter));
        RecyclerView participantsRv = (RecyclerView) findViewById(R.id.participantsRecyclerVIew);
        participantAdapter.initRecyclerView(this, participantsRv);
        button = findViewById(R.id.quitGroup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uId != null && gId != null) {
                    mb.clearListeners();
                    mb.removeUserFromGroup(uId, group.get(0));
                    Intent transition = new Intent(GroupActivity.this, NavigationActivity.class);
                    startActivity(transition);
                }
            }
        });
    }

    public boolean getInfoWrongInput(){
        return wrongInput;
    }
}
