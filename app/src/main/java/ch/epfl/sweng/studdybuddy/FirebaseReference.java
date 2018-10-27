package ch.epfl.sweng.studdybuddy;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseReference implements ReferenceWrapper {
    //pass it
    /**
     * Firebase reference implementation
     */

    //Reference to firebase
    private final DatabaseReference ref;

    public FirebaseReference() {
        ref = FirebaseDatabase.getInstance().getReference();
    }


    public FirebaseReference(DatabaseReference firebaseRef) {
        this.ref = firebaseRef;
    }

    public DatabaseReference getRef() { return ref; }

    public FirebaseReference getParent() { return new FirebaseReference(ref.getParent()); }

    @Override
    public ReferenceWrapper select(String key) {
        return new FirebaseReference(ref.child(key));
    }

    @Override
    public Task<Void> setVal(Object o) {
        return ref.setValue(o);
    }

    @Override
    public Task<Void> clear(){
        return ref.removeValue();
    }


    private <T> ValueEventListener getAllValueEventListener(final Class<T> type, final Consumer<List<T>> callback) {
        return new ValueEventListener() {
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
    }

    private <T> ValueEventListener getValueEventListener(final Class<T> type, final Consumer<T> callback) {
             return new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        callback.accept(dataSnapshot.getValue(type));
                    }
                    catch (Exception e) {
                        Log.e("FATAL ERROR", e.getMessage());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
    }

    @Override
    public ReferenceWrapper parent() {
        return new FirebaseReference(ref.getParent());
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