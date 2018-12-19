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

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.CreateMeetingActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.calendar.ColorController;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.Observable;
import ch.epfl.sweng.studdybuddy.tools.Observer;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Resultable;
import ch.epfl.sweng.studdybuddy.util.ActivityHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.controllers.GroupController.inviteFriendsListener;
import static ch.epfl.sweng.studdybuddy.controllers.GroupController.leaveOnClick;
import static ch.epfl.sweng.studdybuddy.controllers.GroupController.processResult;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.onClickLaunch;


public class GroupActivity extends AppCompatActivity implements Observer, Resultable {
    List<User> participants  = new ArrayList<>();
    MetaGroupAdmin mb  = new MetaGroupAdmin();
    private String uId;
    private String gId;
    Button button;
    private MetaMeeting metaM = new MetaMeeting();
    String adminId;
    private List<Meeting> meetingList;
    private static final int CalendarWidth = 8;
    private RecyclerView.Adapter adapter;
    GridLayout calendarGrid;
    private float NmaxUsers;
    private Pair pair = new Pair();


    private ColorController colorController = new ColorController();

    RecyclerView meetingRV;
    Button inviteFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setupUserGroupAdmin();
        setUI();
        setupMeetings();
        FloatingActionButton actionButton = findViewById(R.id.createMeeting);
        actionButton.setOnClickListener(goTo(CreateMeetingActivity.class, this));
        setupAvails();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent d){
        processResult(requestCode, resultCode, this);
    }

    public void onResult() {
        GroupActivity.onResult(GlobalBundle.getInstance().getSavedBundle(), metaM, gId);
    }

    public static void onResult(Bundle data, MetaMeeting metaM, String gId) {
        MeetingLocation meetingLocation = new MeetingLocation(data);
        metaM.pushLocation(meetingLocation, new ID<>(gId), new ID<>(data.getString(Messages.meetingID)));
    }

    public void setupAvails() {
        Button abutton = findViewById(R.id.editAvail);
        pair.setKey(gId);
        pair.setValue(uId);
        /*if(pair.getKey() == null || pair.getValue() == null){
            throw new NullPointerException("the intent didn't content expected data");
        }*/
        new ConnectedCalendar(this, new ID<>(pair.getKey()));

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



    public static View.OnClickListener goTo(Class<?> to, Activity from) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from.startActivity(new Intent(from, to));
            }
        };
    }

    public void setUI(){
        setContentView(R.layout.activity_group);
        ParticipantAdapter participantAdapter = new ParticipantAdapter(participants);
        mb.addListenner(new RecyclerAdapterAdapter(participantAdapter));
        RecyclerView participantsRv = (RecyclerView) findViewById(R.id.participantsRecyclerVIew);
        participantAdapter.initRecyclerView(this, participantsRv);
        button = findViewById(R.id.quitGroup);
        meetingRV = findViewById(R.id.meetingRV);
        calendarGrid = findViewById(R.id.calendarGrid);
        meetingRV.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton actionButton = findViewById(R.id.createMeeting);
        Intentable toCreation = new Intentable(this, new Intent(this, CreateMeetingActivity.class));
        actionButton.setOnClickListener(onClickLaunch(toCreation));
        Intentable toInviteFriends = new Intentable(this, new Intent(this, InviteFriendsActivity.class));
        inviteFriends = findViewById(R.id.invite_friends);
        inviteFriends.setOnClickListener(inviteFriendsListener(toInviteFriends, gId, uId));
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



    /**
     * change the color of every cell of the calendar when a change has been added to
     * the availabilities of the users.
     *
     * @param observable the object (here the ConnectedCalendar) which is observed by the activity
     */
    @Override
    public void update(Observable observable) {
        List<Integer> groupAvailabilities = ((ConnectedCalendar) observable).getComputedAvailabilities();
        if(groupAvailabilities.size() == ConnectedCalendar.CALENDAR_SIZE) {
            colorController.updateColor(calendarGrid, groupAvailabilities, NmaxUsers, CalendarWidth);
        }
    }


}
