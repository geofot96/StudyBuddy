package ch.epfl.sweng.studdybuddy.activities.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

public class GroupInfoActivity extends AppCompatActivity{
    List<User> participants  = new ArrayList<>();
    MetaGroupAdmin mb  = new MetaGroupAdmin();
    private RecyclerView participantsRv;
    private ParticipantAdapter participantAdapter;
    private String uId;
    private String gId;
    Button button;
    List<Group> group = new ArrayList<>();
    List<String> gIds = new ArrayList<>();
   // private Boolean isParticipant = false; //not used
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        uId = ((StudyBuddy) GroupInfoActivity.this.getApplication()).getAuthendifiedUser().getUserID().getId();
        Bundle origin = GlobalBundle.getInstance().getSavedBundle();
        gId = origin.getString(Messages.groupID);
        gIds.add(gId);
        mb.getGroupsfromIds(gIds, group);
        mb.getGroupUsers(gId, participants);
        setUI();
    }

    public void setUI(){
        participantAdapter = new ParticipantAdapter(participants);
        mb.addListenner(new RecyclerAdapterAdapter(participantAdapter));
        participantsRv = (RecyclerView) findViewById(R.id.participantsRecyclerVIew);
        participantAdapter.initRecyclerView(this, participantsRv);
        button = findViewById(R.id.quitGroup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uId != null && gId != null) {
                    mb.clearListeners();
                    mb.removeUserFromGroup(uId, group.get(0));
                    Intent transition = new Intent(GroupInfoActivity.this, NavigationActivity.class);
                    startActivity(transition);
                }
            }
        });
    }
}
