package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Metabase {

    private ReferenceWrapper db;

    public Metabase(ReferenceWrapper db) {
        this.db = db;
    }

    public ReferenceWrapper getReference() {
        return db;
    }

    public ValueEventListener getUserGroups(String userId, List<Group> groups) {
        return getUserGroups(userId, new ArrayList<>(), groups);
    }

    public ValueEventListener getUserGroups(String userId, List<String> gIds, List<Group> groups) {
        return db.select("userGroups").getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                groups.clear();
                gIds.clear();
                for(Pair p : pairs) {
                    String gId = p.getValue();
                    if(p.getKey().equals(userId) && !gIds.contains(gId)) {
                        gIds.add(gId);
                    }
                }
                getGroupsfromIds(gIds, groups);
            }
        });
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
                        sizes.put(groupID, 1 + sizes.getOrDefault(groupID, 0));
                    }
                }
            }
        });
    }

    /*public ValueEventListener getGroupUsers(String gId, List<String> uIds, List<User> groupUsers) {
        return db.select("userGroups").getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                uIds.clear();
                groupUsers.clear();
                for(Pair p: pairs) {
                    String uId = p.getKey();
                    if()
                }
            }
        });
    }

    public ValueEventListener getUsersfromIds(List<String> uIds, List<User> groupUsers) {

    }*/

    /*
    public void getCourseUsers(String course){}
    *
    * */
}
