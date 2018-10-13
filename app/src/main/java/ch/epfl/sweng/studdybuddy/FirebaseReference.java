package ch.epfl.sweng.studdybuddy;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                snapshot = dataSnapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public FirebaseReference(DatabaseReference firebaseRef) {
        this.ref = firebaseRef;
    }

    @Override
    public ReferenceWrapper select(String key) {
        return new FirebaseReference(ref.child(key));
    }

    @Override
    public Object get() {
        return snapshot.getValue();
    }

    @Override
    public Task<Void> setVal(Object o) {
        return ref.setValue(o);
    }

    @Override
    public Task<Void> clear(){
        return ref.removeValue();
    }
}