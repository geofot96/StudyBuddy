package ch.epfl.sweng.studdybuddy.activities.group;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.firebase.OnGetDataListener;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Notifiable;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.ActivityHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.services.calendar.Color.updateColor;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.calendarEventListener;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.calendarGetDataListener;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.readData;

public class GroupActivity extends AppCompatActivity implements Notifiable {
    private boolean wrongInput = false;
    List<User> participants  = new ArrayList<>();
    MetaGroupAdmin mb  = new MetaGroupAdmin();
    private String uId;
    private String gId;
    Button button;
    List<Group> group = new ArrayList<>();
    List<String> gIds = new ArrayList<>();
    private MetaMeeting metaM = new MetaMeeting();
    String adminId;
    private List<Meeting> meetingList;
    private static final int CalendarWidth = 8;
    private RecyclerView.Adapter adapter;
    GridLayout calendarGrid;
    private float NmaxUsers;
    private Availability userAvailabilities;
    private ConnectedCalendar calendar;
    private DatabaseReference database;
    private Pair pair = new Pair();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setupUserGroupAdmin();
        setUI();
        setupMeetings();
        FloatingActionButton actionButton = findViewById(R.id.createGroup);
        actionButton.setOnClickListener(goTo(createMeetingActivity.class, this));
        setupAvails();
        setOnToggleBehavior(calendarGrid);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent d){
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle data = GlobalBundle.getInstance().getSavedBundle();
            MeetingLocation meetingLocation = new MeetingLocation(
                    data.getString(Messages.LOCATION_TITLE),
                    data.getString(Messages.ADDRESS),
                    data.getDouble(Messages.LATITUDE, 0),
                    data.getDouble(Messages.LONGITUDE, 0)
            );
            metaM.pushLocation(meetingLocation, new ID<>(gId), new ID<>(data.getString(Messages.meetingID)));
        }
    }

    public void setupAvails() {
        calendarGrid = findViewById(R.id.calendarGrid);
        Button button = findViewById(R.id.confirmSlots);
        pair.setKey(gId);
        pair.setValue(uId);
        if(pair.getKey() == null || pair.getValue() == null){
            throw new NullPointerException("the intent didn't content expected data");
        }
        connect();
    }

    public void setupUserGroupAdmin() {
        GlobalBundle globalBundle = GlobalBundle.getInstance();
        globalBundle.putAll(getIntent().getExtras());
        Bundle origin = globalBundle.getSavedBundle();
        uId = ((StudyBuddy) GroupActivity.this.getApplication()).getAuthendifiedUser().getUserID().getId();
        gId = origin.getString(Messages.groupID);
        gIds.add(gId);
        mb.getGroupsfromIds(gIds, group);
        mb.getGroupUsers(gId, participants);
        adminId = origin.getString(Messages.ADMIN);
        if(gId == null || adminId == null ) {
            goTo(NavigationActivity.class, this);
            String TAG = "MEETINGS_ACTIVITY";
            Log.d(TAG, "Information of the group is not fully recovered");
        }
        NmaxUsers = (float) origin.getInt(Messages.maxUser, -1);
    }

    private void goToActivity(Intent intent){
        startActivity(intent);
    }

    @Override
    public void getNotified() {
        update();
    }


    public View.OnClickListener goTo(Class<?> to, Activity from) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(from, to));
            }
        };
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

    public void setupMeetings() {
        RecyclerView meetingRV = findViewById(R.id.meetingRV);
        meetingRV.setLayoutManager(new LinearLayoutManager(this));

        meetingList = new ArrayList<>();

        adapter = new MeetingRecyclerAdapter(this, this, meetingList, new Pair(gId, adminId));

        metaM.getMeetingsOfGroup(new ID<>(gId), ActivityHelper.getConsumerForMeetings(meetingList, metaM, new ID<>(gId), adapter));

        meetingRV.setAdapter(adapter);
    }

    public boolean getInfoWrongInput(){
        return wrongInput;
    }
    public void connect() {
        calendar = new ConnectedCalendar(new ID<>(pair.getKey()), new HashMap<>());

        database = FirebaseDatabase.getInstance().getReference("availabilities").child(pair.getKey());
        database.addChildEventListener(calendarEventListener(calendar, this));

        readData(database.child(pair.getValue()), calendarGetDataListener(callbackCalendar()));
    }

    public Consumer<List<Boolean>> callbackCalendar() {
        return new Consumer<List<Boolean>>() {
            @Override
            public void accept(List<Boolean> booleans) {
                userAvailabilities = new ConnectedAvailability(pair.getValue(), pair.getKey(), new ConcreteAvailability(booleans), new FirebaseReference());
            }
        };
    }
    /**
     * Set the behavior of every cell of the calendar so that
     * clicking on any cell will modify our availabilities in the
     * appropriate time slot
     * @param calendarGrid the View of the calendar
     */
    public void setOnToggleBehavior(GridLayout calendarGrid){
        for(int i = 0; i < calendarGrid.getChildCount(); i++)
        {
            CardView cardView = (CardView) calendarGrid.getChildAt(i);
            int column = i%CalendarWidth;
            if(column!=0) {//Hours shouldn't be clickable
                int row = i/CalendarWidth;
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userAvailabilities.modifyAvailability(row, column-1);
                    }
                });
            }
        }
    }

    /**
     * clicking on the confirm button leads us to GroupActivity
     */
    public void confirmSlots() {
        Intent newIntent = new Intent(this, GroupActivity.class);
        startActivity(newIntent);
    }

    /**
     * change the color of every cell of the calendar when a change has been added to
     * the availabilities of the users.
     */
    public void update() {
        List<Integer> groupAvailabilities = calendar.getComputedAvailabilities();
        if(groupAvailabilities.size() == 77) {
            updateColor(calendarGrid, groupAvailabilities, NmaxUsers, CalendarWidth);
        }
    }


}
