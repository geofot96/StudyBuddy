package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

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
                    if(uIds.contains(users.get(i).getUserID().toString())) {
                        groupUsers.add(users.get(i));
                    }
                }
                notif();
            }
        });
    }
    public void addListenner(AdapterAdapter ad) {
        this.ads.add(ad);
    }

    public void clearListeners() { this.ads.clear(); }

}
