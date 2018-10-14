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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirebaseWrapper implements DatabaseWrapper {

    ReferenceWrapper databaseBackend;

    public FirebaseWrapper(ReferenceWrapper databaseBackend) {
        this.databaseBackend = databaseBackend;
    }

    private final List<Course> aggregateCourses(Object jsonData) {
        return null;
    }

    @Override
    public List<Course> getCourses() {
        return aggregateCourses(databaseBackend.select("courses").get());
    }

    @Override
    public Group getGroup(ID<Group> id) {
        ReferenceWrapper group = databaseBackend.select("groups").select(id.getID());
        int maxNoUsers = Integer.parseInt(group.select("maxNoUsers").toString());
        String courseName = databaseBackend.select("courses").select(group.select("courseID").get().toString()).toString();
        //TODO
        //We should avoid passing references as parameters
        //Pass IDs instead
        ArrayList<User> participants = new ArrayList<>();
        return new Group(maxNoUsers, new Course(courseName), group.select("lang").get().toString(), participants );
    }

    @Override
    public List<Group> getAllGroups() {
        ReferenceWrapper group = databaseBackend.select("groups");
        List<Group> groups = new ArrayList<>();
        return groups;
    }

    @Override
    public void setGroup(ID<Group> id, Group newGroup) {

        //databaseBackend.select("groups").select(newGroup.getGroupID().toString()).setVal(newGroup);

    }

    @Override
    public void putGroup(Group newGroup) {
        databaseBackend.select("groups").select(newGroup.getGroupID().toString()).setVal(newGroup);

    }

    @Override
    public void removeGroup(ID<Group> id) {
        databaseBackend.select("groups").select(id.getID()).clear();
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
    public void deleteFriendShip(ID<Friendship> id) {

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
