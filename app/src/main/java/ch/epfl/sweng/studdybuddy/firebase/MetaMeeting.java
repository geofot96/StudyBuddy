package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;

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

    public void pushMeeting(Meeting meeting, Group group) {
        String mid = meeting.getId().getId();
        db.select("meetings").select(group.getGroupID().getId()).select(mid).setVal(meeting);
    }

    public ValueEventListener fetchMeetings(String groupId, Consumer<List<Meeting>> callback) {
        return db.select("meetings").select(groupId).getAll(Meeting.class, callback);
    }
}
