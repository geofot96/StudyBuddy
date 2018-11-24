package ch.epfl.sweng.studdybuddy.activities.group.meetings;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class MeetingsActivity extends AppCompatActivity {

    private String groupId;
    private String adminId;

    private Bundle origin;

    private RecyclerView meetingRV;
    private RecyclerView.Adapter adapter;

    private List<Meeting> meetingList;

    private MetaMeeting metaM;

    private final String TAG = "MEETINGS_ACTIVITY";

    private final Comparator<Meeting> comparator = new Comparator<Meeting>() {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return (Long.compare(o1.getStarting(), o2.getStarting()));
        }
    };

    private final Consumer<List<Meeting>> consumer = new Consumer<List<Meeting>>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void accept(List<Meeting> meetings) {
            meetingList.clear();
            Date currentDate = new Date();
            for (Meeting m : meetings) {
                if (m.getStarting() < currentDate.getTime()) {
                    metaM.deleteMeeting(m.getId(), new ID<>(groupId));
                } else {
                    meetingList.add(m);
                }
            }
            meetingList.sort(comparator);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        origin = GlobalBundle.getInstance().getSavedBundle();

        groupId = origin.getString(Messages.groupID);
        adminId = origin.getString(Messages.ADMIN);

        if(groupId == null || adminId == null ){
            startActivity(new Intent(this, NavigationActivity.class));
            Log.d(TAG, "Information of the group is not fully recovered");
        }

        meetingRV = findViewById(R.id.meetingRV);
        meetingRV.setLayoutManager(new LinearLayoutManager(this));

        meetingList = new ArrayList<>();

        metaM = new MetaMeeting();

        metaM.getMeetingsOfGroup(new ID<>(groupId), consumer);

        adapter = new MeetingRecyclerAdapter(this, this, meetingList, new Pair(groupId, adminId));

        meetingRV.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent d){
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle data = GlobalBundle.getInstance().getSavedBundle();
            MeetingLocation meetingLocation = new MeetingLocation(
              data.getString(Messages.LOCATION_TITLE),
                    data.getString(Messages.ADDRESS),
                    data.getDouble(Messages.LATITUDE, 0),
                    data.getDouble(Messages.LONGITUDE, 0)
            );
            metaM.pushLocation(meetingLocation, new ID<>(groupId), new ID<>(data.getString(Messages.meetingID)));
        }
    }
}
