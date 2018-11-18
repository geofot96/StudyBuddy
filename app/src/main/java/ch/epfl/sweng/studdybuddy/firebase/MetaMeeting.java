package ch.epfl.sweng.studdybuddy.firebase;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.util.Helper;

public class MetaMeeting extends MetaGroup {
    public MetaMeeting() {
        super();
    }

    public MetaMeeting(ReferenceWrapper rw) {
        super(rw);
    }
    public void pushMeeting(Meeting meeting, Group group) {
        String mid = meeting.getId().getId();
        Pair binding = new Pair(mid, group.getGroupID().getId());
        db.select("meetings").select(mid).setVal(meeting);
        db.select("meetGroups").select(Helper.hashCode(binding)).setVal(binding);
    }






}
