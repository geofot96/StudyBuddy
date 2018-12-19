package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class MetaMeeting extends MetaGroup {
    private String m = Messages.FirebaseNode.MEETINGS;

    public MetaMeeting() {
        super();
    }

    public MetaMeeting(ReferenceWrapper rw) {
        super(rw);
    }

    public void pushMeeting(Meeting meeting, ID<Group> groupID) {
        db.select(m).select(groupID.getId()).select(meeting.getId().getId()).setVal(meeting);
    }

    public void pushMeeting(Meeting meeting, Group group) {
        String mid = meeting.getId().getId();
        db.select(m).select(group.getGroupID().getId()).select(mid).setVal(meeting);
    }

    public void pushLocation(MeetingLocation location, ID<Group> groupID, ID<Meeting> meetingID){
        db.select(m).select(groupID.getId()).select(meetingID.getId()).select("location").setVal(location);

    }

    public void deleteMeeting(ID<Meeting> meetingID, ID<Group> groupID) {
        db.select(m).select(groupID.getId()).select(meetingID.getId()).clear();
    }

    public ValueEventListener getMeetingsOfGroup(ID<Group> groupID, Consumer<List<Meeting>> consumer){
        return db.select(m).select(groupID.getId()).getAll(Meeting.class, consumer);
    }



    public ValueEventListener fetchMeetings(String groupId, Consumer<List<Meeting>> callback) {
        return db.select(m).select(groupId).getAll(Meeting.class, callback);
    }
}
