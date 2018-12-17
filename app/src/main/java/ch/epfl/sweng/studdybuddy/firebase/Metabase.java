package ch.epfl.sweng.studdybuddy.firebase;


import android.support.annotation.Nullable;
import android.view.View;


import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Buddy;
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
        if (ad != null) {
            this.ads.add(ad);
        }
    }

    protected void notif() {
        if (ads != null) {
            for (AdapterAdapter ad : ads)
                ad.update();
        }
    }

    public ValueEventListener getUsersfromIds(List<String> uIds, List<User> groupUsers) {
        return getUsersFromIdsAndConsume(uIds, groupUsers, Consumer.doNothing());
    }

    public ValueEventListener getUsersFromIdsAndConsume(List<String> uIds, List<User> groupUsers, Consumer<List<User>> consumer) {
        return db.select("users").getAll(User.class, new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) {
                for (int i = 0; i < users.size(); ++i) {
                    User u = users.get(i);
                    ID<User> id = u.getUserID();
                    if (u != null && id != null && uIds.contains(users.get(i).getUserID().toString())) {
                        groupUsers.add(users.get(i));
                    }
                }
                if (consumer != null) {
                    consumer.accept(groupUsers);
                }
                notif();
            }
        });
    }

    public ValueEventListener getUserAndConsume(String uId, Consumer<User> consumer) {
        return db.select(Messages.FirebaseNode.USERS).select(uId).get(User.class, consumer);
    }

    //The update function from FB API is not appropriate
    public ValueEventListener updateUserCourses(String uid, List<String> update, Intentable mother) {
        return db.select(Messages.FirebaseNode.USERCOURSE).getAll(Pair.class, new Consumer<List<Pair>>() {
            private void updateField(List<String> diff, Pair pair) {
                String val = pair.getValue();
                if (!diff.contains(val)) {
                    db.select(Messages.FirebaseNode.USERCOURSE).select(Helper.hashCode(pair)).clear();
                }
            }

            @Override
            public void accept(List<Pair> pairs) {
                db.muteAll();
                clearListeners();
                List<String> remainder = new ArrayList<>(update);
                //update.clear();
                for (Pair pair : pairs) {
                    String val = pair.getValue();
                    if (pair.getKey().equals(uid)) {
                        updateField(remainder, pair);
                    }
                }
                putAllCourses(remainder, uid);
                mother.launch();
            }
        });
    }

    public void putAllCourses(List<String> courseSelection, String userid) {
        for (String course : courseSelection) {
            Pair pair = new Pair(userid, course);
            db.select("userCourse").select(Helper.hashCode(pair).toString()).setVal(pair);
        }
    }

    public void addListenner(AdapterAdapter ad) {
        this.ads.add(ad);
    }

    public void clearListeners() {
        this.ads.clear();
    }


    public ValueEventListener getBuddies(String uid, List<User> users) {
        return getBuddiesAndConsume(uid, users, new LinkedList<>(), Consumer.doNothing());
    }

    public ValueEventListener getBuddiesAndConsume(String uid, List<User> users, List<String> uIdsToFill, Consumer<List<User>> consumer) {
        return db.select(Messages.FirebaseNode.BUDDIES).getAll(Buddy.class, new Consumer<List<Buddy>>() {
            @Override
            public void accept(@Nullable List<Buddy> buddies) {
                for (Buddy buddy : buddies) {
                    String bob = buddy.buddyOf(uid);
                    if (bob != null) {
                        uIdsToFill.add(bob);
                    }
                }

                getUsersFromIdsAndConsume(uIdsToFill, users, consumer);
            }
        });
    }


    public ValueEventListener fetchUser(List<User> usernames) {
        return db.select(Messages.FirebaseNode.USERS).getAll(User.class, new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> users) {
                usernames.clear();
                for (User user : users) {
                    usernames.add(user);
                }
                notif();
            }
        });
    }

    public ValueEventListener fetchUserNames(List<String> usernames) {
        return db.select(Messages.FirebaseNode.USERS).getAll(User.class, new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> users) {
                usernames.clear();
                for (User user : users) {
                    usernames.add(user.getName());
                }
                notif();
            }
        });
    }

    public View.OnClickListener updateFriendsOnDone(String uId, List<User> friendsSelection, MetaGroupAdmin mga, Intentable mother) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mga.putAllFriends(friendsSelection, uId );
                for (User friend : friendsSelection) {
                    Buddy buddy = new Buddy(uId, friend.getUserID().getId());
                    db.select(Messages.FirebaseNode.BUDDIES).select(buddy.hash()).setVal(buddy);
                    mother.launch();
                }
            }
        };
    }
}
