package ch.epfl.sweng.studdybuddy.services.calendar;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.OnGetDataListener;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;


/**
 * The ConnectedAvailability is linked to an instance of Availability and will
 * update the database every time the availabilities are updated
 */
public class ConnectedAvailability implements Availability {
    private final DatabaseReference databaseReference;
    private Availability availabilities;

    public ConnectedAvailability(@NonNull ID<User> user, @NonNull ID<Group> group){
        this(FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Messages.FirebaseNode.AVAILABILITIES)
                .child(group.getId())
                .child(user.getId())
        );
    }

    public ConnectedAvailability(DatabaseReference databaseReference) {
        if(databaseReference == null){
            throw new IllegalArgumentException();
        }

        List<Boolean> data = new ArrayList<>();
        readData(databaseReference, availabilityGetDataListener(callbackAvailabilities(data)));

        this.availabilities = new ConcreteAvailability(data);
        this.databaseReference = databaseReference;
    }

    public ConnectedAvailability(Availability A, DatabaseReference databaseReference){
        if(A == null || databaseReference == null){
            throw new IllegalArgumentException();
        }
        this.availabilities = A;
        this.databaseReference = databaseReference;
    }


    @Override
    public List<Boolean> getUserAvailabilities() {
        return availabilities.getUserAvailabilities();
    }

    public Boolean isAvailable(int row, int column){
        return availabilities.isAvailable(row, column);
    }

    @Override
    public void modifyAvailability(int row, int column) throws ArrayIndexOutOfBoundsException {
        availabilities.modifyAvailability(row, column);
        update();
    }

    private void update(){
        databaseReference.setValue(availabilities.getUserAvailabilities());
    }

    public static void removeAvailabiliity(ID<Group> group, ID<User> user, ReferenceWrapper ref){
        ref.select(Messages.FirebaseNode.AVAILABILITIES).select(group.getId()).select(user.getId()).clear();
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


    public  Consumer<List<Boolean>> callbackAvailabilities(List<Boolean> absorber) {
        return new Consumer<List<Boolean>>() {
            @Override
            public void accept(List<Boolean> booleans) {
                absorber.clear();
                absorber.addAll(booleans);
            }
        };
    }

    /**
     * this listener will wait for the data contained in firebase so that
     * we can initialize <tt>userAvailabilities</tt> with the newly retrieved
     * list of booleans
     */
    public OnGetDataListener availabilityGetDataListener(Consumer<List<Boolean>> callback) {
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
