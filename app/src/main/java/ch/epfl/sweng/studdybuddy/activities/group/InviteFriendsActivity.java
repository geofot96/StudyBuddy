package ch.epfl.sweng.studdybuddy.activities.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Buddy;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.tools.BuddyAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class InviteFriendsActivity extends AppCompatActivity {
    private String gId, uId;
    private RecyclerView rv ;
    private List<User> buddies;
    private List<User> participants;

    private BuddyAdapter buddyAdapter;
    private         MetaGroup mg = new MetaGroup();
    private final String BOUBA = "TnGyoumVk2SDqYCQx0psDttd3Ea2";
    private final String GEORGE = "RfxAvevZKyZFfjqrvRLq7hmHDdu2";
    private final String YANN = "ZqHa4GLFGEfbuQW9gO6E0kndWNw2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        getBundleData();

        mg.pushBuddies(new Buddy(BOUBA, GEORGE));
        mg.pushBuddies(new Buddy(BOUBA, YANN));
        buddies = new ArrayList<>();
        participants = new ArrayList<>();
        rv = findViewById(R.id.buddiesToInvite);
        buddyAdapter = new BuddyAdapter(buddies);
        mg.addListenner(new RecyclerAdapterAdapter(buddyAdapter));
        buddyAdapter.initRecyclerView(this, rv);
        mg.getBuddiesNotInGroup(gId, uId, participants, buddies);


    }
    private void getBundleData(){
        Bundle bundle = GlobalBundle.getInstance().getSavedBundle();
        gId = bundle.getString(Messages.groupID);
        uId = bundle.getString(Messages.userID);
    }








}
