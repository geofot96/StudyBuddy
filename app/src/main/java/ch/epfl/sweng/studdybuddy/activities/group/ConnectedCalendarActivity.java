package ch.epfl.sweng.studdybuddy.activities.group;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import ch.epfl.sweng.studdybuddy.activities.group.GroupActivity;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.OnGetDataListener;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;

/**
 * On this activity we're able as a user of the group to see
 * all availabilities of each user of the group and update our own
 * availabilities dynamically. Touching a cell of the calendar will
 * modify our availability
 */
public class ConnectedCalendarActivity extends AppCompatActivity
{
    GridLayout calendarGrid;
    private static final int CalendarWidth = 8;
    private float NmaxUsers;
    private Availability userAvailabilities;
    private ConnectedCalendar calendar;
    private DatabaseReference database;
    private Pair pair = new Pair();

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException{
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        calendarGrid = findViewById(R.id.calendarGrid);
        Button button = findViewById(R.id.confirmSlots);

        GlobalBundle globalBundle = GlobalBundle.getInstance();
        Bundle origin = globalBundle.getSavedBundle();

        NmaxUsers = (float) origin.getInt(Messages.maxUser, -1);

        pair.setKey(origin.getString(Messages.groupID));
        pair.setValue(origin.getString(Messages.userID));
        if(pair.getKey() == null || pair.getValue() == null){
            throw new NullPointerException("the intent didn't content expected data");
        }

        connect();

        setOnToggleBehavior(calendarGrid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                confirmSlots();
            }
        });
    }

    public void connect() {
        calendar = new ConnectedCalendar(new ID<>(pair.getKey()), new HashMap<>());

        database = FirebaseDatabase.getInstance().getReference("availabilities").child(pair.getKey());
        database.addChildEventListener(new AvailabilitiesChildEventListener());

        readData(database.child(pair.getValue()), new AvailabilitiesOnDataGetListener());
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
            updateColor(groupAvailabilities);
        }
    }

    public void updateColor(List<Integer> av){
        int size = calendarGrid.getColumnCount() * calendarGrid.getRowCount();
        for (int i = 0; i < size; i++) {
            CardView cardView;
            if (i % CalendarWidth != 0) {//Hours shouldn't be clickable
                cardView = (CardView) calendarGrid.getChildAt(i);
                int index = (i / CalendarWidth) * (CalendarWidth - 1) + (i % CalendarWidth) - 1;
                cardView.setCardBackgroundColor(gradient((float) av.get(index)));
            }
        }
    }

    /**
     * return the color picked in a dynamically set color gradient
     * according to the ratio of available users in the targeted time slot
     * the returned color is <tt>Color.WHITE</tt> when no one is available
     * and <tt>Color.GREEN</tt> when every one is available
     *
     * @param nAvailableUser the number of available users in this time slot
     * @return the right color according to the ration of available users
     */
    private int gradient(float nAvailableUser){
        float[] hsv = new float[3];
        Color.colorToHSV(Color.WHITE, hsv);
        float s = hsv[1];
        Color.colorToHSV(Color.GREEN, hsv);
        float coef = nAvailableUser/NmaxUsers;
        hsv[1] = s +  coef * (hsv[1] - s);
        return Color.HSVToColor(hsv);
    }

    /**
     * reading only once data in the database synchronously
     *
     * @param db the node of the database where we want to retrieve some data
     * @param listener from {@link OnGetDataListener}
     */
    public void readData(DatabaseReference db, final OnGetDataListener listener){
        listener.onStart();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailure();
            }
        });
    }

    /**
     * this ChildEventListener will set after any changes in the users availabilities
     * the calendar to keep the activity updated with firebase
     */
    private class AvailabilitiesChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            String targetID = dataSnapshot.getKey();
            FirebaseReference fb = new FirebaseReference(database.child(targetID));
            fb.getAll(Boolean.class, new Consumer<List<Boolean>>() {
                @Override
                public void accept(List<Boolean> booleans) {
                    calendar.modify(targetID, booleans);
                    update();
                }
            });
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            String targetID = dataSnapshot.getKey();
            calendar.removeUser(targetID);
            update();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    /**
     * this listener will wait for the data contained in firebase so that
     * we can initializing <tt>userAvailabilities</tt> with the newly retrieved
     * list of booleans
     */
    private class AvailabilitiesOnDataGetListener implements OnGetDataListener{
        @Override
        public void onSuccess(DataSnapshot dataSnapshot) {
            List<Boolean> list = new ArrayList<>();
            for(DataSnapshot ds: dataSnapshot.getChildren()){
                list.add(ds.getValue(Boolean.class));
            }
            userAvailabilities = new ConnectedAvailability(pair.getValue(), pair.getKey(), new ConcreteAvailability(list), new FirebaseReference());
        }

        @Override
        public void onStart() {
            Log.d("ON START", "retrieve availabilities of the user");
        }

        @Override
        public void onFailure() {
            Log.d("ON FAILURE", "didn't retrieve availabailities of the user");
        }
    }
}
