package ch.epfl.sweng.studdybuddy.tools;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.OnGetDataListener;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;

public class AvailabilitiesHelper {

    /**
     * this ChildEventListener will set after any changes in the users availabilities
     * the calendar to keep the activity updated with firebase
     */
    public static ChildEventListener calendarEventListener(ConnectedCalendar calendar, Notifiable callback, DatabaseReference database) {
        return  new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String targetID = dataSnapshot.getKey();
                FirebaseReference fb = new FirebaseReference(database.child(targetID));
                fb.getAll(Boolean.class, new Consumer<List<Boolean>>() {
                    @Override
                    public void accept(List<Boolean> booleans) {
                        calendar.modify(targetID, booleans);
                        callback.getNotified();
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String targetID = dataSnapshot.getKey();
                calendar.removeUser(targetID);
                callback.getNotified();
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
    }
    /**
     * reading only once data in the database synchronously
     *
     * @param db the node of the database where we want to retrieve some data
     * @param listener from {@link OnGetDataListener}
     */
    public static void readData(DatabaseReference db, final OnGetDataListener listener){
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
     * this listener will wait for the data contained in firebase so that
     * we can initializing <tt>userAvailabilities</tt> with the newly retrieved
     * list of booleans
     */
    public static OnGetDataListener calendarGetDataListener(Consumer<List<Boolean>> callback) {
        return new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                List<Boolean> list = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    list.add(ds.getValue(Boolean.class));
                }
                callback.accept(list);
            }

            @Override
            public void onStart() {
                Log.d("ON START", "retrieve availabilities of the user");
            }

            @Override
            public void onFailure() {
                Log.d("ON FAILURE", "didn't retrieve availabailities of the user");
            }
        };
    }


}
