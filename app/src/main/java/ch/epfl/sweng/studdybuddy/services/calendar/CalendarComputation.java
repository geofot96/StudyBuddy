package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarComputation {

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


    public List<Integer> sumBooleanLists(List<List<Boolean>> lists){

        int lists_len = lists.size();
        List<Integer> result = new ArrayList<>();
        if(lists_len != 0) {
            int one_list_len = lists.get(0).size();

            for (int i = 0; i < one_list_len; i++) {
                result.add(0);
            }

            for (int j = 0; j < lists_len; j++) {
                List<Integer> current_list = getIntegerListFromBooleanList(lists.get(j));
                result = getSumOfTwoLists(result, current_list);
            }
        }
        return result;
    }

}
