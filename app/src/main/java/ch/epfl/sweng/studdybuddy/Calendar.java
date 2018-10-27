package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;
import java.util.List;

public final class Calendar {

    private ID<Group> groupID;

    //private List<Availability> currnetAvailability;

    public List<Integer> getSumOfTwoLists(List<Integer> first_list, List<Integer> second_list){

        int len = first_list.size();
        List<Integer> result = new ArrayList<>(len);

        for (int i = 0; i < len; i++){
            int element = first_list.get(i) + second_list.get(i);
            result.add(element);
        }
        return result;
    }

    public List<Integer> getIntegerListFromBooleanList(List<Boolean> list){

        int len = list.size();
        List<Integer> result = new ArrayList<>(len);

        for (int i = 0; i < len; i++){
            int element = 0;
            if (list.get(i) == Boolean.TRUE){
                element = 1;
            }
            result.add(element);
        }
        return result;
    }

    /*private List<Availiability> getAvailabilityListsFromFB (){

    }
    
    private List<Integer> getGroupAvailability(){

    }*/
}
