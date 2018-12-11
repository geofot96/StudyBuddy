package ch.epfl.sweng.studdybuddy.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

abstract public class Metabase {
    protected ReferenceWrapper db;
    protected final List<AdapterAdapter> ads; // eventually use option

    public Metabase(ReferenceWrapper db, AdapterAdapter ad) {
        this.db = db;
        this.ads = new LinkedList<>();
        if(ad != null) {this.ads.add(ad);}
    }

    protected void notif() {
        if(ads != null) {
            for(AdapterAdapter ad : ads)
                ad.update();
        }
    }

    public ValueEventListener getUsersfromIds(List<String> uIds, List<User> groupUsers) {
        return db.select("users").getAll(User.class, new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) {
                for(int i = 0; i < users.size(); ++i) {
                    User u = users.get(i);
                    ID<User> id = u.getUserID();
                    if(u != null && id != null && uIds.contains(users.get(i).getUserID().toString())) {
                        groupUsers.add(users.get(i));
                    }
                }
                notif();
            }
        });
    }

    public ValueEventListener getUserAndConsume(String uId, Consumer<User> consumer){
        return db.select(Messages.FirebaseNode.USERS).select(uId).get(User.class, consumer);
    }

    //The update function from FB API is not appropriate
    public ValueEventListener updateUserCourses(String uid, List<String> update, Intentable mother) {
        return db.select(Messages.FirebaseNode.USERCOURSE).getAll(Pair.class, new Consumer<List<Pair>>() {
            private void updateField(List<String> diff, Pair pair) {
                String val = pair.getValue();
                if(!diff.contains(val)) {
                    db.select(Messages.FirebaseNode.USERCOURSE).select(Helper.hashCode(pair)).clear();
                }
            }

            @Override
            public void accept(List<Pair> pairs) {
                db.muteAll();
                clearListeners();
                List<String> remainder = new ArrayList<>(update);
                //update.clear();
                for(Pair pair: pairs) {
                    String val = pair.getValue();
                    if(pair.getKey().equals(uid)) {
                        updateField(remainder, pair);
                    }
                }
                putAllCourses(remainder, uid);
                mother.launch();
            }
        });
    }

    public void putAllCourses(List<String> courseSelection, String userid) {
        for(String course : courseSelection){
            Pair pair = new Pair(userid, course);
            db.select("userCourse").select(Helper.hashCode(pair).toString()).setVal(pair);
        }
    }
    public void addListenner(AdapterAdapter ad) {
        this.ads.add(ad);
    }

    public void clearListeners() { this.ads.clear(); }

}
