package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarComputation {

    public List<Integer> getSumOfTwoLists(List<Integer> firstList, List<Integer> secondList){

        int len = Math.min(firstList.size(), secondList.size());
        List<Integer> result = new ArrayList<>(len);

        for (int i = 0; i < len; i++){
            int element = firstList.get(i) + secondList.get(i);
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

        int listsLen = lists.size();
        List<Integer> result = new ArrayList<>();
        if(listsLen != 0) {
            int oneListLen = Integer.MAX_VALUE;
            for(int i = 0; i<listsLen; i++){
                oneListLen = Math.min(oneListLen, lists.get(i).size());
            }

            for (int i = 0; i < oneListLen; i++) {
                result.add(0);
            }

            for (int j = 0; j < listsLen; j++) {
                List<Integer> current_list = getIntegerListFromBooleanList(lists.get(j));
                result = getSumOfTwoLists(result, current_list);
            }
        }
        return result;
    }

}
