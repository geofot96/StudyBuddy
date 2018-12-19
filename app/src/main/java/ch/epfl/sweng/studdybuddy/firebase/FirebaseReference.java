package ch.epfl.sweng.studdybuddy.firebase;

import android.renderscript.Sampler;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;

public class FirebaseReference implements ReferenceWrapper {
    //pass it
    /**
     * Firebase reference implementation
     */

    //Reference to firebase
    private final DatabaseReference ref;
    private static boolean persistenceEnabled = false;
    private final List<ValueEventListener> listeners;
    private final List<FirebaseReference> children;

    public FirebaseReference() {
        //If I have already set persistence return a new reference”



        //if(this.persistenceEnabled){
        this(FirebaseDatabase.getInstance().getReference());
        /*}
        else {
            FirebaseDatabase temp = FirebaseDatabase.getInstance();
            //temp.setPersistenceEnabled(true);
            persistenceEnabled = true;
            this.ref = temp.getReference();
        }*/

    }


    public FirebaseReference(DatabaseReference firebaseRef) {
        listeners = new ArrayList<>();
        children = new ArrayList<>();
        this.ref = firebaseRef;
    }

    public DatabaseReference getRef() { return ref; }

    public FirebaseReference getParent() { return new FirebaseReference(ref.getParent()); }

    @Override
    public ReferenceWrapper select(String key) {
        FirebaseReference newRef = new FirebaseReference(ref.child(key));
        children.add(newRef);
        return newRef;
    }

    @Override
    public Task<Void> setVal(Object o) {
        return ref.setValue(o);
    }

    public Task<Void> push(Object o) {
        return ref.push().setValue(o);
    }

    @Override
    public Task<Void> clear(){
        return ref.removeValue();
    }


    private <T> ValueEventListener getAllValueEventListener(final Class<T> type, final Consumer<List<T>> callback) {
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> elements = new ArrayList<>();
                try {
                    for(DataSnapshot snap: dataSnapshot.getChildren()) {
                        elements.add(snap.getValue(type));
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                callback.accept(elements);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        listeners.add(vel);
        return vel;
    }

    private <T> ValueEventListener getValueEventListener(final Class<T> type, final Consumer<T> callback) {
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    callback.accept(dataSnapshot.getValue(type));
                }
                catch (Exception e) {
                    // Log.e("FATAL ERROR", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        listeners.add(vel);
        return vel;
    }

    @Override
    public ReferenceWrapper parent() {
        return new FirebaseReference(ref.getParent());
    }

    @Override
    public void muteAll() {
        //Remove listeners to this exact location
        for(ValueEventListener listener: listeners) {
            ref.removeEventListener(listener);
        }
        //Remove recursively listeners to children locations
        for(FirebaseReference ref: children) {
            ref.muteAll();
        }
    }


    public <T> ValueEventListener getAll(Class<T> type, Consumer<List<T>> callback) {
        ValueEventListener res = getAllValueEventListener(type, callback);
        if(ref != null) {
            ref.addValueEventListener(res);
        }
        return res;
    }

    public <T> ValueEventListener get(Class<T> type, Consumer<T> callback) {
        ValueEventListener res = getValueEventListener(type, callback);
        ref.addValueEventListener(res);
        return res;
    }


}