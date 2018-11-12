package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.util.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.util.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

public class GroupInfoActivity extends AppCompatActivity{
    List<User> participants  = new ArrayList<>();
    MetaGroup mb  = new MetaGroup();
    private RecyclerView participantsRv;
    private ParticipantAdapter participantAdapter;
    private String uId;
    private String gId;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        uId = ((StudyBuddy) GroupInfoActivity.this.getApplication()).getAuthendifiedUser().getUserID().getId();
        Intent intent = getIntent();
        if(intent != null){
            gId = intent.getStringExtra(GroupsActivity.GROUP_ID);
            mb.getGroupUsers(gId, participants);

        }
        setUI();
    }

    public void setUI(){
        participantAdapter = new ParticipantAdapter(participants);
        mb.addListenner(new RecyclerAdapterAdapter(participantAdapter));
        participantsRv = (RecyclerView) findViewById(R.id.participantsRecyclerVIew);
        participantAdapter.initRecyclerView(this, participantsRv);
    }




    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(gId == null) {
            getMenuInflater().inflate(R.menu.mymenu, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            if(uId != null && gId != null) {
                mb.removeGroup(new Pair(uId, gId));
                Intent transition = new Intent(this, GroupsActivity.class);
                startActivity(transition);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
