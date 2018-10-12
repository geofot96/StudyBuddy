package ch.epfl.sweng.studdybuddy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.internal.firebase_database.zzit;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

interface ReferenceWrapper {

    ReferenceWrapper select(String key);

    Object getVal(String key);
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

    FirebaseReference(DataSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public ReferenceWrapper select(String key) {
        return new FirebaseReference(snapshot.child(key));
    }

    @Override
    public Object getVal(String key) {
        return snapshot.child(key).getValue();
    }
}

public class FirebaseWrapper implements DatabaseWrapper {

    ReferenceWrapper databaseBackend;

    FirebaseWrapper(ReferenceWrapper databaseBackend) {
        this.databaseBackend = databaseBackend;
    }

    private final List<Course> aggregateCourses(Object jsonData) {

    }

    @Override
    public List<Course> getCourses() {
        return aggregateCourses(databaseBackend.getVal("courses"));
    }

    @Override
    public Group getGroup(GroupId id) {
        ReferenceWrapper groups = databaseBackend.select("groups").select(id.toString());
        int maxNoUsers = Integer.parseInt(groups.getVal("maxNoUsers").toString());
        
        return databaseBackend.;
    }

    @Override
    public List<Group> getAllGroups() {
        return null;
    }

    @Override
    public void setGroup(GroupId groupId, Group newGroup) {

    }

    @Override
    public void putGroup(Group newGroup) {

    }

    @Override
    public void removeGroup(GroupId groupId) {

    }

    @Override
    public List<Group> getUserGroups(UserId userID) {
        return null;
    }

    @Override
    public List<Meeting> getMeetings(UserId userId) {
        return null;
    }

    @Override
    public List<Friendship> getFriends(UserId userID) {
        return null;
    }

    @Override
    public void putFriendShip(Friendship friendship) {

    }

    @Override
    public void putMeeting(Meeting meeting) {

    }

    @Override
    public void deleteMeeting(MeetingId meetingId) {

    }

    @Override
    public void setMeeting(MeetingId meetingId, Meeting meeting) {

    }
}
