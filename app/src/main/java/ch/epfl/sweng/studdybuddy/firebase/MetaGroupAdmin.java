package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

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

    //returns null in case we want to make group immutable down the road
    public Group findNextAdmin(Group g, Iterator<Pair> it) {
        if(g == null || it == null) return null;
        while(it.hasNext()) {
            Pair p = it.next();
            if(p.getValue().equals(g.getGroupID().getId())) {
                return g.withAdmin(p.getKey());
            }
        }
        return null;
    }

    public ValueEventListener removeUserFromGroup(String uId, Group g) {
        db.select(Messages.FirebaseNode.USERGROUP).select(Helper.hashCode(new Pair(uId, g.getGroupID().getId()))).clear(); //redundant
        ConnectedAvailability.removeAvailabiliity(g.getGroupID(), new ID<>(uId), db);
        if(g.getAdminID().equals(uId)) {
            return rotateAdmin(g);
        }
        return null;
    }
}
