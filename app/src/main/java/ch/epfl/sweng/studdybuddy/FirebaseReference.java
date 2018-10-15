package ch.epfl.sweng.studdybuddy;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.Iterator;

public class FirebaseReference implements ReferenceWrapper {
    //pass it
    /**
     * Firebase reference implementation
     */

    //Reference to firebase
    private DatabaseReference ref;

    //Last update snapshot
    private DataSnapshot snapshot;

    public FirebaseReference() {
        ref = FirebaseDatabase.getInstance().getReference();
    }

    public FirebaseReference(DatabaseReference firebaseRef) {
        this.ref = firebaseRef;
    }

    public DatabaseReference getRef() { return ref; }

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

    @Override
    public <T> void get(Class<T> type, Consumer<T> callback) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.accept(dataSnapshot.getValue(type));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public <T> void getAll(Class<T> type, Consumer<List<T>> callback) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<T> elements = new ArrayList<>();
                for(DataSnapshot snap: dataSnapshot.getChildren())
                    elements.add(snap.getValue(type));
                callback.accept(elements);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}