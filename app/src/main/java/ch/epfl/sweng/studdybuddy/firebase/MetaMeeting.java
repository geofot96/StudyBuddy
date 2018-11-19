package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.util.Consumer;

public class MetaMeeting extends MetaGroup {
    public MetaMeeting() {
        super();
    }

    public MetaMeeting(ReferenceWrapper rw) {
        super(rw);
    }

    public void pushMeeting(Meeting meeting, ID<Group> groupID) {
        db.select("meetings").select(groupID.getId()).select(meeting.getId().getId()).setVal(meeting);
    }

    public void deleteMeeting(ID<Meeting> meetingID, ID<Group> groupID) {
        db.select("meetings").select(groupID.getId()).select(meetingID.getId()).clear();
    }

    public ValueEventListener getMeetingsOfGroup(ID<Group> groupID, Consumer<List<Meeting>> consumer){
        return db.select("meetings").select(groupID.getId()).getAll(Meeting.class, consumer);
    }

}
