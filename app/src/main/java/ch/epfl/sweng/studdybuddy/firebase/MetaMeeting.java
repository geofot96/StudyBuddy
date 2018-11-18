package ch.epfl.sweng.studdybuddy.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.util.Consumer;

public class MetaMeeting extends MetaGroup {
    private DatabaseReference database;
    public MetaMeeting() {
        super();
    }

    public MetaMeeting(ReferenceWrapper rw) {
        super(rw);
        database = FirebaseDatabase.getInstance().getReference("meetings");
    }

    public void pushMeeting(Meeting meeting, ID<Group> groupID) {
        db.select("meetings").select(groupID.getId()).select(meeting.getId().getId()).setVal(meeting);
    }

    public void deleteMeeting(ID<Meeting> meetingID, ID<Group> groupID) {
        db.select("meetings").select(groupID.getId()).select(meetingID.getId()).clear();
    }

}
