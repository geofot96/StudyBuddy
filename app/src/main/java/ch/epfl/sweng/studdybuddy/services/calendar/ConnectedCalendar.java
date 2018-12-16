package ch.epfl.sweng.studdybuddy.services.calendar;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Observable;
import ch.epfl.sweng.studdybuddy.tools.Observer;
import ch.epfl.sweng.studdybuddy.util.Messages;

/**
 * The ConnectedCalendar will handle the of every user
 * and update the list of integers, that represents the number of users
 * available at each slot, using the methods of {@link CalendarComputation} class.
 * To do this, it will store and keep an updated collection of the availabilities
 * of every user of the group.
 */

public final class ConnectedCalendar implements Observable{
    public static final int CALENDAR_SIZE = 77;
    private CalendarComputation calendarController;
    private HashMap<String, List<Boolean>> availabilities = new HashMap<>() ;

    private List<Observer> observers = new ArrayList<>();

    // this reference to the database is used to listen every change to the database to update the calendar
    private DatabaseReference databaseReference;

    public ConnectedCalendar(Map<String, List<Boolean>> list){
        calendarController = new CalendarComputation();
        availabilities.putAll(list);
    }

    public ConnectedCalendar(Observer observer, ID<Group> groupID){
        if(groupID == null || observer == null){
            throw new IllegalArgumentException();
        }

        calendarController = new CalendarComputation();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Messages.FirebaseNode.AVAILABILITIES).child(groupID.getId());
        databaseReference.addChildEventListener(calendarEventListener(this, databaseReference));

        addObserver(observer);
    }

    public List<Integer> getComputedAvailabilities() {
        return calendarController.sumBooleanLists(new ArrayList<>(availabilities.values()));
    }

    public void modify(String userID, List<Boolean> userAvailabilities) {
        if(userAvailabilities!=null && userID != null){
            availabilities.put(userID, userAvailabilities);
        }
        notifyObservers();
    }

    public void removeUser(String userID){

        availabilities.remove(userID);
        notifyObservers();
    }


    @Override
    public void addObserver(Observer observer) {
        if(observer == null){
            throw new IllegalArgumentException();
        }
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer:observers){
            observer.update(this);
        }
    }

    /**
     * this ChildEventListener will set after any changes in the users availabilities
     * the calendar to keep the activity updated with firebase
     */
    public static ChildEventListener calendarEventListener(ConnectedCalendar calendar, DatabaseReference database) {
        return  new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String targetID = dataSnapshot.getKey();
                new FirebaseReference(database.child(targetID)).getAll(Boolean.class, new Consumer<List<Boolean>>() {
                    @Override
                    public void accept(List<Boolean> booleans) {
                        calendar.modify(targetID, booleans);
                    }
                });
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                onChildAdded(dataSnapshot, s);
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                calendar.removeUser(dataSnapshot.getKey());
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
    }
}
