package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

/**
 * Extends MetaGroup with queries to handle the Admin of a group
 */
public class MetaGroupAdmin extends MetaGroup {

    public MetaGroupAdmin(ReferenceWrapper rw) {
        super(rw);
    }
    public MetaGroupAdmin() { super(); }
    public ValueEventListener rotateAdmin(Group g) {
        return db.select(Messages.FirebaseNode.USERGROUP).getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                Group updated = findNextAdmin(g, pairs.iterator());
                ReferenceWrapper gField = db.select(Messages.FirebaseNode.GROUPS).select(g.getGroupID().getId());
                if(updated == null) gField.clear(); //Last user left => wipe the group
                else gField.setVal(updated);
            }
        });
    }


    /**
     * Returns a group with the admin rotated
     * @return the group and null if no admin is found
     */
    public Group findNextAdmin(Group g, Iterator<Pair> members) {
        if(g == null || members == null) return null;
        while(members.hasNext()) {
            Pair p = members.next();
            if(p.getValue().equals(g.getGroupID().getId())) {
                return g.withAdmin(p.getKey());
            }
        }
        return null;
    }

    /**
     * Removes the user from a group. Rotates the admin if neceesary.
     * @return the ValueEventListener of admin rotation query, null otherwise
     */
    public ValueEventListener removeUserFromGroup(String uId, Group g) {
        db.select(Messages.FirebaseNode.USERGROUP).select(Helper.hashCode(new Pair(uId, g.getGroupID().getId()))).clear(); //redundant
        ConnectedAvailability.removeAvailabiliity(g.getGroupID(), new ID<>(uId), db);
        if(g.getAdminID().equals(uId)) {
            return rotateAdmin(g);
        }
        return null;
    }
}
