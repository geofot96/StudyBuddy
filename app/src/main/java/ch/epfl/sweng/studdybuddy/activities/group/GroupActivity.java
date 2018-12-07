package ch.epfl.sweng.studdybuddy.activities.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.Notifiable;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Resultable;
import ch.epfl.sweng.studdybuddy.util.ActivityHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.controllers.GroupController.callbackCalendar;
import static ch.epfl.sweng.studdybuddy.controllers.GroupController.leaveOnClick;
import static ch.epfl.sweng.studdybuddy.services.calendar.Color.updateColor;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.calendarEventListener;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.calendarGetDataListener;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.readData;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.onClickLaunch;

public class GroupActivity extends AppCompatActivity implements Notifiable, Resultable {
    private boolean wrongInput = false;
    List<User> participants  = new ArrayList<>();
    MetaGroupAdmin mb  = new MetaGroupAdmin();
    private String uId;
    private String gId;
    Button button;
    List<Group> group = new ArrayList<>();
    private MetaMeeting metaM = new MetaMeeting();
    String adminId;
    private List<Meeting> meetingList;
    private static final int CalendarWidth = 8;
    private RecyclerView.Adapter adapter;
    GridLayout calendarGrid;
    private float NmaxUsers;
    private ConnectedCalendar calendar;
    private DatabaseReference database;
    private Pair pair = new Pair();
    RecyclerView meetingRV;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setupUserGroupAdmin();
        setUI();
        setupMeetings();
        setupAvails();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent d){
        resultActivity(requestCode, resultCode, this);
    }

    public void onResult() {
        GroupActivity.onResult(GlobalBundle.getInstance().getSavedBundle(), metaM, gId);
    }

    public static void onResult(Bundle data, MetaMeeting metaM, String gId) {
        MeetingLocation meetingLocation = new MeetingLocation(
                data.getString(Messages.LOCATION_TITLE),
                data.getString(Messages.ADDRESS),
                data.getDouble(Messages.LATITUDE, 0),
                data.getDouble(Messages.LONGITUDE, 0)
        );
        metaM.pushLocation(meetingLocation, new ID<>(gId), new ID<>(data.getString(Messages.meetingID)));
    }

    public static void resultActivity(int requestCode, int resultCode, Resultable res) {

        if(requestCode == 1 && resultCode == RESULT_OK) {
            res.onResult();
        }
    }

    public void setupAvails() {
        calendarGrid = findViewById(R.id.calendarGrid);
        Button abutton = findViewById(R.id.editAvail);
        pair.setKey(gId);
        pair.setValue(uId);
        /*if(pair.getKey() == null || pair.getValue() == null){
            throw new NullPointerException("the intent didn't content expected data");
        }*/
        calendar = new ConnectedCalendar(new ID<>(pair.getKey()), new HashMap<>());

        database = FirebaseDatabase.getInstance().getReference("availabilities").child(pair.getKey());
        database.addChildEventListener(calendarEventListener(calendar, this, database));

        readData(database.child(pair.getValue()), calendarGetDataListener(callbackCalendar(pair, this)));
        Intentable toCalendar = new Intentable(this, new Intent(this, ConnectedCalendarActivity.class));
        abutton.setOnClickListener(onClickLaunch(toCalendar));
    }

    public void setupUserGroupAdmin() {
        GlobalBundle globalBundle = GlobalBundle.getInstance();
        globalBundle.putAll(getIntent().getExtras());
        Bundle origin = globalBundle.getSavedBundle();
        uId = ((StudyBuddy) GroupActivity.this.getApplication()).getAuthendifiedUser().getUserID().getId();
        gId = origin.getString(Messages.groupID);
        mb.onGroupGet(gId, new Consumer<Group>() {
            @Override
            public void accept(Group group) {
                Intentable toNavig = new Intentable(GroupActivity.this, new Intent(GroupActivity.this, NavigationActivity.class));
                button.setOnClickListener(leaveOnClick(mb, uId, group, toNavig));
            }
        });
        mb.getGroupUsers(gId, participants);
        adminId = origin.getString(Messages.ADMIN);
        /*if(gId == null || adminId == null ) {
            goTo(NavigationActivity.class, this);
            String TAG = "MEETINGS_ACTIVITY";
            Log.d(TAG, "Information of the group is not fully recovered");
        }*/
        NmaxUsers = (float) origin.getInt(Messages.maxUser, -1);
    }

    @Override
    public void getNotified() {
        update();
    }

    public void setUI(){
        setContentView(R.layout.activity_group);
        ParticipantAdapter participantAdapter = new ParticipantAdapter(participants);
        mb.addListenner(new RecyclerAdapterAdapter(participantAdapter));
        RecyclerView participantsRv = (RecyclerView) findViewById(R.id.participantsRecyclerVIew);
        participantAdapter.initRecyclerView(this, participantsRv);
        button = findViewById(R.id.quitGroup);
        meetingRV = findViewById(R.id.meetingRV);
        meetingRV.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton actionButton = findViewById(R.id.createMeeting);
        Intentable toCreation = new Intentable(this, new Intent(this, createMeetingActivity.class));
        actionButton.setOnClickListener(onClickLaunch(toCreation));
    }

    public void setupMeetings() {
        meetingList = new ArrayList<>();
        Bundle bundle = GlobalBundle.getInstance().getSavedBundle();
        bundle.putString(Messages.groupID, gId);
        bundle.putString(Messages.ADMIN, adminId);
        adapter = new MeetingRecyclerAdapter(this, this, meetingList, bundle);
        metaM.getMeetingsOfGroup(new ID<>(gId), ActivityHelper.getConsumerForMeetings(meetingList, metaM, new ID<>(gId), adapter));
        meetingRV.setAdapter(adapter);
    }

    public boolean getInfoWrongInput(){
        return wrongInput;
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
