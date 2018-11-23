package ch.epfl.sweng.studdybuddy.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.util.Helper.getOrDefault;
import static ch.epfl.sweng.studdybuddy.util.Helper.safeAddId;

public class MetaGroup extends Metabase{

    public MetaGroup() {
        this(new FirebaseReference());
    }

    public MetaGroup(ReferenceWrapper db) {
        this(db, null);
    }

    public MetaGroup(ReferenceWrapper db, AdapterAdapter ad) {
        super(db, ad);
    }

    public MetaGroup(AdapterAdapter ad) {
        this(new FirebaseReference(), ad);
    }

    public ValueEventListener getUserGroups(String userId, List<Group> groups) {
        return getUserGroups(userId, new ArrayList<>(), groups);
    }

    public ValueEventListener getUserGroups(String userId, List<String> gIds, List<Group> groups) {
        return db.select(Messages.FirebaseNode.USERGROUP).getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                groups.clear();
                gIds.clear();
                for(Pair p : pairs) {
                    safeAddId(userId, p.getKey(), p.getValue(), gIds);
                }
                getGroupsfromIds(gIds, groups);
            }
        });
    }

    public ValueEventListener getGroup(String id, Group g, AdapterAdapter adapter) {
        return db.select(Messages.FirebaseNode.GROUPS).select(id).get(Group.class, new Consumer<Group>() {
            @Override
            public void accept(Group group) {
                g.copy(group);
                adapter.update();
            }
        });
    }
    //protected ?
    public ValueEventListener getGroupsfromIds(List<String> gIds, List<Group> userGroups) {
        return db.select(Messages.FirebaseNode.GROUPS).getAll(Group.class, groupsFromIds(gIds, userGroups));
    }

    private Consumer<List<Group>> groupsFromIds(List<String> gIds, List<Group> userGroups) {
        return new Consumer<List<Group>>() {
            @Override
            public void accept(List<Group> groups) {
                for(Group g : groups) {
                    if(gIds.contains(g.getGroupID().toString())) {
                        userGroups.add(g);
                    }
                }
                notif();
            }
        };
    }

    public ValueEventListener getUserCourses(String uId, List<String> courses) {
        return db.select(Messages.FirebaseNode.USERCOURSE).getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                courses.clear();
                for(Pair p: pairs) {
                    if(p.getKey().equals(uId)) {
                        courses.add(p.getValue());
                    }
                }
                notif();
            }
        });
    }

    public ValueEventListener getAllGroupSizes(Map<String, Integer> sizes) {
        return db.select(Messages.FirebaseNode.USERGROUP).getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                sizes.clear();
                for(Pair pair : pairs) {
                    if(pair != null) {
                        String groupID = pair.getValue();
                        sizes.put(groupID, 1 + getOrDefault(groupID, sizes));
                    }
                }
                notif();
            }
        });
    }

    public ValueEventListener getGroupUsers(String gId, List<User> groupUsers) {
        return getGroupUsers(gId, new LinkedList<>(), groupUsers);
    }

    public ValueEventListener getGroupUsers(String gId, List<String> uIds, List<User> groupUsers) {
        return db.select(Messages.FirebaseNode.USERGROUP).getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                uIds.clear();
                for(int i = 0; i < pairs.size(); ++i) {
                    safeAddId(gId, pairs.get(i).getValue(), pairs.get(i).getKey(), uIds);
                }
                groupUsers.clear();
                getUsersfromIds(uIds, groupUsers);
            }
        });
    }

    public void pushGroup(Group g, String creatorId) {
        db.select(Messages.FirebaseNode.GROUPS).select(g.getGroupID().getId()).setVal(g);
        Pair pair = new Pair(creatorId,g.getGroupID().toString());
        db.select(Messages.FirebaseNode.USERGROUP).select(Helper.hashCode(pair)).setVal(pair);
    }

}
