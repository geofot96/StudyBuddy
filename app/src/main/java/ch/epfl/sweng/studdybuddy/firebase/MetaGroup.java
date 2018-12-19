package ch.epfl.sweng.studdybuddy.firebase;


import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studdybuddy.core.Buddy;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.util.Helper.getOrDefault;
import static ch.epfl.sweng.studdybuddy.util.Helper.safeAddId;

 /*
 * Extends MetaBase with queries for Groups
 */
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


    /**
     * Adds all groups of the user with the given ID to the list of groups
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getUserGroups(String userId, List<Group> groups) {
        return getUserGroups(userId, new ArrayList<>(), groups);
    }

    /**
     * Adds all groups of the user with the given ID to the list of groups
     * Additionnaly fills the list gIds with the groups IDs
     * @return the ValueEventListener of the database query
     */
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

    /*
     * Adds all groups with the given IDs to the list of userGroups
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getGroupsfromIds(List<String> gIds, List<Group> userGroups) {
        return db.select(Messages.FirebaseNode.GROUPS).getAll(Group.class, new Consumer<List<Group>>() {
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


    /**
     * Adds the ID of all courses a user is attending to the list of courses
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getUserCourses(String uId, List<String> coursesIDs) {
        return db.select(Messages.FirebaseNode.USERCOURSE).getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                coursesIDs.clear();
                for(Pair p: pairs) {
                    if(p.getKey().equals(uId)) {
                        coursesIDs.add(p.getValue());
                    }
                }
                notif();
            }
        });
    }


    /**
     * Fills the map with a map from Group IDs to group sizes
     * @param sizes
     * @return the ValueEventListenr of the dabase query
     */
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

    /**
     * Fills the list with all study groups of the user
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getGroupUsers(String gId, List<User> groupUsers) {
        return getGroupUsers(gId, new LinkedList<>(), groupUsers);
    }


    /**
     * Fills the given with respectvely the list of userIDs and users of the group members
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getGroupUsers(String gId, List<String> uIds, List<User> groupUsers) {
      return getGroupUsersAndConsume(gId, uIds, groupUsers, Consumer.doNothing());
    }


    /**
     * Fills the given with respectvely the list of userIDs and users of the group members
     * The consumer accepts the list of users once retrieved
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getGroupUsersAndConsume(String gId, List<String> uIds, List<User> groupUsers, Consumer<List<User>> consumer){
        return db.select(Messages.FirebaseNode.USERGROUP).getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                uIds.clear();
                for(int i = 0; i < pairs.size(); ++i) {
                    safeAddId(gId, pairs.get(i).getValue(), pairs.get(i).getKey(), uIds);
                }
                groupUsers.clear();
                getUsersFromIdsAndConsume(uIds, groupUsers, consumer);
            }
        });
    }


    /**
     * Fills the groupMembers with the members of the group with the given groupID
     * Fills the buddies list with the friends of the user with the given userID.
     * The list of buddies is filtered with the list of groupMembers. Hence the list of buddies will contain
     * the list of friends that are not in group with the given ID.
     * @param gId the group ID
     * @param uId the user ID
     * @param groupMembers the users in the group with the givenID
     * @param buddies the friends of the user with the given user ID who are not in the group
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener getBuddiesNotInGroup(String gId, String uId,  List<User> groupMembers, List<User> buddies ){
        return getBuddiesAndConsume(uId, buddies, new LinkedList<>(), FirebaseConsumers.filterBuddies(gId, new ArrayList<>(), groupMembers, this));
    }

    /**
     * Pushes the group to the database
     */
    public void pushGroup(Group g, String creatorId) {
        db.select(Messages.FirebaseNode.GROUPS).select(g.getGroupID().getId()).setVal(g);
        Pair pair = new Pair(creatorId,g.getGroupID().toString());
        db.select(Messages.FirebaseNode.USERGROUP).select(Helper.hashCode(pair)).setVal(pair);
    }

    /**
     * Fetches a user from the database and call the Consumer accept function
     * @return the ValueEventListener of the database query
     */
    public ValueEventListener onGroupGet(String gid, Consumer<Group> callback) {
        return db.select(Messages.FirebaseNode.GROUPS).select(gid).get(Group.class, new Consumer<Group>() {
            @Override
            public void accept(Group group) {
                if(group != null) {
                    callback.accept(group);
                }
            }
        });
    }

     /**
      * Push buddy to database
      */
     public void pushBuddies(Buddy buddy){
         db.select(Messages.FirebaseNode.BUDDIES).select(buddy.hash()).setVal(buddy);
     }


 }
