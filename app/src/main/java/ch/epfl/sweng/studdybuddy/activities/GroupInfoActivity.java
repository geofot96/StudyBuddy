package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.util.Holder;
import ch.epfl.sweng.studdybuddy.util.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.util.RecyclerAdapterAdapter;

public class GroupInfoActivity extends AppCompatActivity{
    List<User> participants;
    MetaGroup mb;
    private RecyclerView participantsRv;
    private ParticipantAdapter participantAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        participants = new ArrayList<>();
        mb  = new MetaGroup();
        Intent intent = getIntent();
        String gId = intent.getStringExtra(GroupsActivity.GROUP_ID);
        mb.getGroupUsers(gId, participants);
        setUI();
    }

    public void setUI(){
        participantAdapter = new ParticipantAdapter(participants);
        mb.addListenner(new RecyclerAdapterAdapter(participantAdapter));
        participantsRv = (RecyclerView) findViewById(R.id.participantsRecyclerVIew);
        participantsRv.setLayoutManager(new LinearLayoutManager(this));
        participantsRv.setAdapter(participantAdapter);
    }
}
