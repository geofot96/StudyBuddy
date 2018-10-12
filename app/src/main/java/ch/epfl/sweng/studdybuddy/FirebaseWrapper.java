package ch.epfl.sweng.studdybuddy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.internal.firebase_database.zzit;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


interface ReferenceWrapper {

    ReferenceWrapper select(String key);

    Object getVal(String key);

    Task<Void> setVal(Object o);
}


class FirebaseReference implements ReferenceWrapper {
    //pass it

    DatabaseReference ref;
    DataSnapshot snapshot;

    FirebaseReference() {
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

    FirebaseReference(DatabaseReference snapshot) {
        this.ref = snapshot;
    }

    @Override
    public ReferenceWrapper select(String key) {
        return new FirebaseReference(ref.child(key));
    }

    @Override
    public Object getVal(String key) {
        return snapshot.child(key).getValue();
    }

    @Override
    public Task<Void> setVal(Object o) {
        return ref.setValue(o);
    }
}

abstract public class FirebaseWrapper implements DatabaseWrapper {

    ReferenceWrapper databaseBackend;

    FirebaseWrapper(ReferenceWrapper databaseBackend) {
        this.databaseBackend = databaseBackend;
    }

    private final List<Course> aggregateCourses(Object jsonData) {
        return null;
    }

    @Override
    public List<Course> getCourses() {
        return aggregateCourses(databaseBackend.getVal("courses"));
    }

    @Override
    public Group getGroup(ID<Group> id) {
        ReferenceWrapper group = databaseBackend.select("groups").select(id.toString());
        int maxNoUsers = Integer.parseInt(group.getVal("maxNoUsers").toString());
        String courseName = databaseBackend.select("courses").getVal(group.getVal("courseID").toString()).toString();
        //TODO
        //We should avoid passing references as parameters
        //Pass IDs instead
        ArrayList<User> participants = new ArrayList<>();
        return new Group(maxNoUsers, new Course(courseName), group.getVal("lang").toString(), participants );
    }

    @Override
    public List<Group> getAllGroups() {
        ReferenceWrapper group = databaseBackend.select("groups");
        List<Group> groups = new ArrayList<>();
        /*for(DataSnapshot g : group ){
            groups.add(getGroup(g.get));
        }*/
        return groups;
    }

    @Override
    public void setGroup(ID<Group> id, Group newGroup) {

    }

    @Override
    public void putGroup(Group newGroup) {
        databaseBackend.setVal(newGroup);
    }

    @Override
    public void removeGroup(ID<Group> id) {

    }

    @Override
    public List<Group> getUserGroups(ID<User> id) {
        return null;
    }

    @Override
    public List<Meeting> getMeetings(ID<User> id) {
        return null;
    }

    @Override
    public List<Friendship> getFriends(ID<User> id) {
        return null;
    }

    @Override
    public void putFriendShip(Friendship friendship) {

    }

    @Override
    public void putMeeting(Meeting meeting) {

    }

    @Override
    public void deleteMeeting(ID<Meeting> id) {

    }

    @Override
    public void setMeeting(ID<Meeting> id, Meeting meeting) {

    }
}
