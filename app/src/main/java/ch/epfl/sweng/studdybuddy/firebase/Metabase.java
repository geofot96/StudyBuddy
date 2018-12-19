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

/**
 * Abstract Wrapper around a Database instance
 * This class offers advanced ooperations on the database
 */
abstract public class Metabase {
    /**
     * Database instance
     */
    protected ReferenceWrapper db;

    /**
     * The list of adapters we can update
     */
    protected final List<AdapterAdapter> ads; // eventually use option



    public Metabase(ReferenceWrapper db, AdapterAdapter ad) {
        this.db = db;
        this.ads = new LinkedList<>();
        if (ad != null) {
            this.ads.add(ad);
        }
    }

    /**
     * Updates all adapters
     *
     */
    protected void notif() {
        if (ads != null) {
            for (AdapterAdapter ad : ads)
                ad.update();
        }
    }

    /**
     * Fills the list of users according to a list of user IDs
     * Notifies all ArrayAdapters listeners once the callback is done.
     * @return the ValueEventListener of the database interaction
     */
    public ValueEventListener getUsersfromIds(List<String> uIds, List<User> groupUsers) {
        return getUsersFromIdsAndConsume(uIds, groupUsers, Consumer.doNothing());
    }

    /**
     * Fills the list of users according to a list of user IDs and
     *  calls the callback consumer on the filled list.
     * Notifies all ArrayAdapters listeners once the callback has accepted the data.
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getUsersFromIdsAndConsume(List<String> uIds, List<User> groupUsers, Consumer<List<User>> consumer) {
        return db.select(Messages.FirebaseNode.USERS).getAll(User.class, new Consumer<List<User>>() {
            @Override
            public void accept(List<User> users) {
                groupUsers.clear();
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


    /**
     * Calls the consumer accept function on the fetched user
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getUserAndConsume(String uId, Consumer<User> consumer) {
        return db.select(Messages.FirebaseNode.USERS).select(uId).get(User.class, consumer);
    }

    /**
     * Update the list of user courses
     * @return the ValueEventListener of the database query
     */
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

    /**
     * Puts the list of courses in the database
     *
     */
    public void putAllCourses(List<String> courseSelection, String userid) {
        for (String course : courseSelection) {
            Pair pair = new Pair(userid, course);
            db.select("userCourse").select(Helper.hashCode(pair).toString()).setVal(pair);
        }
    }

    /**
     * Adds the adapter to the list of adapters to notify when some data changes
     */
    public void addListenner(AdapterAdapter ad) {
        this.ads.add(ad);
    }

    /**
     * Clears the list of adapters notified on a data change
     */
    public void clearListeners() {
        this.ads.clear();
    }


    /**
     * Fills the users list with the friends of the user with ID uid
     * @return the ValueEventListener of the databaseTransaction
     */
    public ValueEventListener getBuddies(String uid, List<User> users) {
        return getBuddiesAndConsume(uid, users, new LinkedList<>(), Consumer.doNothing());
    }

    /**
     * Adds all friends of the users with the given ID to the list of users.
     * Additionnaly fills the list of ids with the friendsIDs.
     * The consumer will accept the list of users before notifying the adapters
     * @return the ValueEventListener of the database query
     */
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


    /**
     * Fills the list with all users stored in the database
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener fetchUser(List<User> users) {
        return db.select(Messages.FirebaseNode.USERS).getAll(User.class, new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> users) {
                users.clear();
                for (User user : users) {
                    users.add(user);
                }
                notif();
            }
        });
    }

    /**
     * Fills the list with all usernamess of the users stored in the database
     * @return the ValueEventListener of the database query
     */
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

    /**
     * Returns an OnClickListener which adds a friendship relation between
     * the user with the given uId and a list of users.
     * @return the OnClickListener
     */
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
