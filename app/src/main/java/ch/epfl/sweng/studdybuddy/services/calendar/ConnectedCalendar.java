package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.Consumer;

public class ConnectedCalendar implements CalendarInterface{
    Calendar calendar;
    FirebaseReference ref;
    List<List<Boolean>> groupsAvailabilities;

    public ConnectedCalendar(ID<Group> groupID){
        this(new Calendar(groupID));
    }

    public ConnectedCalendar(Calendar calendar){
        this(calendar, new FirebaseReference());
    }

    public ConnectedCalendar(Calendar calendar, FirebaseReference ref) {
        this.calendar = calendar;
        this.ref = ref;
        List<List<Boolean>> init = new ArrayList<>();
        ref.select("availabilities").getAll(HashMap.class, new Consumer<List<HashMap>>() {
            @Override
            public void accept(List<HashMap> hashMaps) {
                for(HashMap map : hashMaps){
                    for(Object list : map.values() ){
                        init.add((List<Boolean>) list);
                    }
                }
            }
        });
        this.groupsAvailabilities = init;
    }
    

    public List<Integer> getGroupsAvailabilities() {
        return sumBooleanLists(groupsAvailabilities);
    }


    @Override
    public List<Integer> sumBooleanLists(List<List<Boolean>> lists) {
        return calendar.sumBooleanLists(lists);
    }

    public String getID() {
        return calendar.getID();
    }


}
