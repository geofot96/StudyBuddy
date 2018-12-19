package ch.epfl.sweng.studdybuddy.firebase;

import android.support.annotation.Nullable;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.Consumer;


public class FirebaseConsumers {
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
