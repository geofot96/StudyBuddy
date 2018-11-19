package ch.epfl.sweng.studdybuddy.activities.meetings;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class MeetingsActivity extends AppCompatActivity {

    private String groupId;

    private Bundle origin;

    private RecyclerView meetingRV;
    private RecyclerView.Adapter adapter;

    private HashMap<String, Meeting> hashMapMeetings;
    private List<Meeting> meetingList;

    private MetaMeeting metaM;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        origin = getIntent().getExtras();

        groupId = origin.getString(Messages.groupID);

        meetingRV = findViewById(R.id.meetingRV);
        meetingRV.setLayoutManager(new LinearLayoutManager(this));

        meetingList = new ArrayList<>();

        metaM = new MetaMeeting();


        metaM.getMeetingsOfGroup(new ID<>(groupId), new Consumer<List<Meeting>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void accept(List<Meeting> meetings) {
                meetingList.clear();
                Date currentDate = new Date();
                for(Meeting m: meetings){
                    if (m.getStarting().before(currentDate)){
                        metaM.deleteMeeting(m.getId(), new ID<>(groupId));
                    } else {
                        meetingList.add(m);
                    }
                }
                meetingList.sort(new Comparator<Meeting>() {
                    @Override
                    public int compare(Meeting o1, Meeting o2) {
                        return o1.getStarting().compareTo(o2.getStarting());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new MeetingRecyclerAdapter(meetingList, this);

        meetingRV.setAdapter(adapter);

    }

}
