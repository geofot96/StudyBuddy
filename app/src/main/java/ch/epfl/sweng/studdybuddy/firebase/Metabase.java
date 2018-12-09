package ch.epfl.sweng.studdybuddy.firebase;

import android.support.annotation.Nullable;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Buddy;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
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
    public void addListenner(AdapterAdapter ad) {
        this.ads.add(ad);
    }

    public void clearListeners() { this.ads.clear(); }

    //Will override old friendship but will not create doublon
    public void befriend(String uid, String friend) {
        Buddy buddy = new Buddy(uid, friend);
        db.select(Messages.FirebaseNode.BUDDIES).select(buddy.hash()).setVal(buddy);
    }

    public ValueEventListener getBuddies(String uid, List<User> users) {
        return db.select(Messages.FirebaseNode.BUDDIES).getAll(Buddy.class, new Consumer<List<Buddy>>() {
            @Override
            public void accept(@Nullable List<Buddy> buddies) {
                List<String> buddiesIDs = new ArrayList<>();
                for(Buddy buddy: buddies) {
                    String bob = buddy.buddyOf(uid);
                    if(bob != null) {
                        buddiesIDs.add(bob);
                    }
                }
                getUsersfromIds(buddiesIDs, users);
            }
        });
    }
}
