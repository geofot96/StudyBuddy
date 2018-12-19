package ch.epfl.sweng.studdybuddy.firebase;

import android.support.annotation.Nullable;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

public class FirebaseConsumers {
    /**
     * Consumer  which removes all users present in the accepted list from the list of buddies
     * @param buddies the users to filter
     * @param metaGroup the metaGroup to notify
     * @return a
     */
    public static Consumer<List<User>> filterBuddiesWithParticipants(List<User> buddies, MetaGroup metaGroup){
        return new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> participants) {
                for(User user: participants){
                    if(buddies.contains(user)){
                        buddies.remove(user);
                    }
                }
                metaGroup.notif();
            }
        };
    }

    /**
     * Fills the lists of users with respectively, the list of group members and the list of the user friends
     * Additionaly fills the list of uIds with the user friends IDs
     * @param gId the group ID
     * @param uIds the user IDs to fill
     * @param participants the list of group members
     * @param metaGroup the metaGroup used to fetch data
     * @return the consumer
     */
    public static Consumer<List<User>> filterBuddies(String gId, List<String> uIds, List<User> participants, MetaGroup metaGroup){
        return new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> buddies) {
                uIds.clear();
                for(User buddy:  buddies){
                    uIds.add(buddy.getUserID().getId());
                }
                metaGroup.getGroupUsersAndConsume(gId, uIds, participants, FirebaseConsumers.filterBuddiesWithParticipants(buddies, metaGroup));
            }
        };
    }

}
