package ch.epfl.sweng.studdybuddy.activities.meetings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class MeetingsActivity extends AppCompatActivity {

    private String groupId;

    private Bundle origin;

    private RecyclerView meetingRV;
    private RecyclerView.Adapter adapter;

    private HashMap<String, Meeting> hashMapMeetings;
    private List<Meeting> meetingList;

    private MetaMeeting metaM;

    private DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        origin = getIntent().getExtras();

        groupId = origin.getString(Messages.groupID);

        meetingRV = findViewById(R.id.meetingRV);
        meetingRV.setLayoutManager(new LinearLayoutManager(this));

        hashMapMeetings = new HashMap<>();
        meetingList = new ArrayList<>();

        metaM = new MetaMeeting();

        db = FirebaseDatabase.getInstance().getReference("meetings").child(groupId);
        db.addChildEventListener(new MeetingsChildListener());

        adapter = new MeetingRecyclerAdapter(meetingList, this);

        meetingRV.setAdapter(adapter);

    }

    private class MeetingsChildListener implements ChildEventListener{

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            String targetID = dataSnapshot.getKey();
            Meeting newMeeting = dataSnapshot.getValue(Meeting.class);
            if(newMeeting.getStarting().compareTo(new Date()) < 0){
                metaM.deleteMeeting(newMeeting.getId(), new ID<>(groupId));
            }else{
                hashMapMeetings.put(targetID, newMeeting);
            }
            meetingList = new ArrayList<>(hashMapMeetings.values());
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            onChildAdded(dataSnapshot,s);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            hashMapMeetings.remove(dataSnapshot.getKey());
            meetingList = new ArrayList<>(hashMapMeetings.values());
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
