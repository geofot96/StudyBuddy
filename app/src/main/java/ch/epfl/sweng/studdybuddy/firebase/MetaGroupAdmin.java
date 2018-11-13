package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;

public class MetaGroupAdmin extends MetaGroup {

    public MetaGroupAdmin(ReferenceWrapper rw) {
        super(rw);
    }
    public MetaGroupAdmin() { super(); }
    public ValueEventListener rotateAdmin(Group g) {
        return db.select("userGroup").getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                Group updated = findNextAdmin(g, pairs.iterator());
                ReferenceWrapper gField = db.select("groups").select(g.getGroupID().getId());
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
        db.select("userGroup").select(Helper.hashCode(new Pair(uId, g.getGroupID().getId()))).clear(); //redundant
        if(g.getAdminID().equals(uId)) {
            return rotateAdmin(g);
        }
        return null;
    }

}
