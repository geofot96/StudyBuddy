package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studdybuddy.util.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.util.Helper;

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
        return db.select("userGroup").getAll(Pair.class, new Consumer<List<Pair>>() {
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

    public void addListenner(AdapterAdapter ad) {
        this.ads.add(ad);
    }

    private void safeAddId(String ref, String neu, String val, List<String> ids) {
        if(ref.equals(neu) && !ids.contains(val)) {
            ids.add(val);
        }
    }

    //protected ?
    public ValueEventListener getGroupsfromIds(List<String> gIds, List<Group> userGroups) {
        return db.select("groups").getAll(Group.class, groupsFromIds(gIds, userGroups));
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
        return db.select("userCourse").getAll(Pair.class, new Consumer<List<Pair>>() {
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
        return db.select("userGroup").getAll(Pair.class, new Consumer<List<Pair>>() {
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

    private int getOrDefault(String key, Map<String, Integer> map) {
        if(map.containsKey(key)) return map.get(key);
        else return 0;
    }

    public ValueEventListener getGroupUsers(String gId, List<User> groupUsers) {
        return getGroupUsers(gId, new LinkedList<>(), groupUsers);
    }

    public ValueEventListener getGroupUsers(String gId, List<String> uIds, List<User> groupUsers) {
        return db.select("userGroup").getAll(Pair.class, new Consumer<List<Pair>>() {
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

    public ValueEventListener getUsersfromIds(List<String> uIds, List<User> groupUsers) {
        return db.select("users").getAll(User.class, new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) {
                for(int i = 0; i < users.size(); ++i) {
                    if(uIds.contains(users.get(i).getUserID().toString())) {
                        groupUsers.add(users.get(i));
                    }
                }
                notif();
            }
        });
    }

    public void pushGroup(Group g, String creatorId) {
        db.select("groups").select(g.getGroupID().getId()).setVal(g);
        Pair pair = new Pair(creatorId,g.getGroupID().toString());
        db.select("userGroup").select(Helper.hashCode(pair)).setVal(pair);
    }

}
