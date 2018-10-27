package ch.epfl.sweng.studdybuddy;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Metabase {

    private ReferenceWrapper db;
    private final List<AdapterAdapter> ads; // eventually use option

    public Metabase() {
        this(new FirebaseReference());
    }

    public Metabase(ReferenceWrapper db) {
        this(db, null);
    }

    public Metabase(ReferenceWrapper db, AdapterAdapter ad) {
        this.db = db;
        this.ads = new LinkedList<>();
        if(ad != null) {this.ads.add(ad);}
    }

    public Metabase(AdapterAdapter ad) {
        this(new FirebaseReference(), ad);
    }

    public ReferenceWrapper getReference() {
        return db;
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
        return db.select("groups").getAll(Group.class, new Consumer<List<Group>>() {
            @Override
            public void accept(List<Group> groups) {
                for(Group g : groups) {
                    if(gIds.contains(g.getGroupID().toString())) {
                        userGroups.add(g);
                    }
                }
                notif();
            }
        });
    }

    public ValueEventListener getUserCourses(String uId, List<String> courses) {
        return db.select("userCourses").getAll(Pair.class, new Consumer<List<Pair>>() {
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
        return db.select("userGroups").getAll(Pair.class, new Consumer<List<Pair>>() {
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

    public int getOrDefault(String key, Map<String, Integer> map) {
        if(map.containsKey(key)) return map.get(key);
        else return 0;
    }

    public ValueEventListener getGroupUsers(String gId, List<User> groupUsers) {
        return getGroupUsers(gId, new LinkedList<>(), groupUsers);
    }

    public ValueEventListener getGroupUsers(String gId, List<String> uIds, List<User> groupUsers) {
        return db.select("userGroups").getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                uIds.clear();
                groupUsers.clear();
                for(Pair p: pairs) {
                    safeAddId(gId, p.getValue(), p.getKey(), uIds);
                }
                getUsersfromIds(uIds, groupUsers);
            }
        });
    }

    public ValueEventListener getUsersfromIds(List<String> uIds, List<User> groupUsers) {
        return db.select("users").getAll(User.class, new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) {
                for(User u: users) {
                    if(uIds.contains(u.getUserID().toString())) {
                        groupUsers.add(u);
                    }
                }
                notif();
            }
        });
    }

    private void notif() {
        if(ads != null) {
            for(AdapterAdapter ad : ads)
                ad.update();
        }
    }

    /*
    public void getCourseUsers(String course){}
    *
    * */
}
