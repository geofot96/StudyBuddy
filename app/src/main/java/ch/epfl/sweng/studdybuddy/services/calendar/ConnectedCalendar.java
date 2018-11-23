package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;

/**
 * The ConnectedCalendar will handle the of every user
 * and update the list of integers, that represents the number of users
 * available at each slot, using the methods of {@link Calendar} class.
 * To do this, it will store and keep an updated collection of the availabilities
 * of every user of the group.
 */

public final class ConnectedCalendar {
    private Calendar calendar;
    private HashMap<String, List<Boolean>> availabilities = new HashMap<>() ;

    public ConnectedCalendar(ID<Group> groupID, HashMap<String, List<Boolean>> list){
        calendar = new Calendar(groupID);
        availabilities.putAll(list);
    }


    public String getID() {
        return calendar.getID();
    }


    public List<Integer> getComputedAvailabilities() {
        return calendar.sumBooleanLists(new ArrayList<>(availabilities.values()));
    }

    public void modify(String userID, List<Boolean> userAvailabilities) {
        if(userAvailabilities!=null && userID != null){
            availabilities.put(userID, userAvailabilities);
        }
    }

    public void removeUser(String userID){
        availabilities.remove(userID);
    }


}
