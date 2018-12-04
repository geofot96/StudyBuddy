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
import ch.epfl.sweng.studdybuddy.services.calendar.Calendar;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Notifiable;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.services.calendar.Color.updateColor;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.calendarEventListener;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.calendarGetDataListener;
import static ch.epfl.sweng.studdybuddy.tools.AvailabilitiesHelper.readData;

/**
 * On this activity we're able as a user of the group to see
 * all availabilities of each user of the group and update our own
 * availabilities dynamically. Touching a cell of the calendar will
 * modify our availability
 */
/**
 * On this activity we're able as a user of the group to see
 * all availabilities of each user of the group and update our own
 * availabilities dynamically. Touching a cell of the calendar will
 * modify our availability
 */
public class ConnectedCalendarActivity extends AppCompatActivity implements Notifiable
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

        retrieveData();
        connect();

        setOnToggleBehavior(calendarGrid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ConnectedCalendarActivity.this, GroupActivity.class));
            }
        });
    }

    void retrieveData() {
        GlobalBundle globalBundle = GlobalBundle.getInstance();
        Bundle origin = globalBundle.getSavedBundle();

        NmaxUsers = (float) origin.getInt(Messages.maxUser, -1);

        pair.setKey(origin.getString(Messages.groupID));
        pair.setValue(origin.getString(Messages.userID));
        if(pair.getKey() == null || pair.getValue() == null){
            throw new NullPointerException("the intent didn't content expected data");
        }

    }

    public void connect() {
        calendar = new ConnectedCalendar(new ID<>(pair.getKey()), new HashMap<>());

        database = FirebaseDatabase.getInstance().getReference("availabilities").child(pair.getKey());
        database.addChildEventListener(calendarEventListener(calendar, this, database));

        readData(database.child(pair.getValue()), calendarGetDataListener(callbackCalendar()));
    }
    public Consumer<List<Boolean>> callbackCalendar() {
        return new Consumer<List<Boolean>>() {
            @Override
            public void accept(List<Boolean> booleans) {
                userAvailabilities = new ConnectedAvailability(pair.getValue(), pair.getKey(), new ConcreteAvailability(booleans), new FirebaseReference());
                update();
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
     * change the color of every cell of the calendar when a change has been added to
     * the availabilities of the users.
     */
    public void update() {
        List<Integer> groupAvailabilities = calendar.getComputedAvailabilities();
        if(groupAvailabilities.size() == 77) {
            updateColor(calendarGrid, groupAvailabilities, NmaxUsers, CalendarWidth);
        }
    }

    public void getNotified() {
        update();
    }

}
