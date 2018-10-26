package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.ValueEventListener;

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

    /*public ValueEventListener getUserGroups() {

    }

    public ValueEventListener getUserCourses() {

    }*/

    public ValueEventListener getAllGroupSizes(Map<String, Integer> sizes) {
        return db.select("userGroups").getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                sizes.clear();
                for(Pair pair : pairs) {
                    if(pair != null) {
                        String groupID = pair.getKey();
                        sizes.put(groupID, 1 + sizes.get(groupID));
                    }
                }
            }
        });
    }

    /*public ValueEventListener getSizeOfGroup() {

    }*/
}
