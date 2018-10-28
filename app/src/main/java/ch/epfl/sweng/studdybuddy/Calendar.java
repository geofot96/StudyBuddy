package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;
import java.util.List;

public final class Calendar {

    private ID<Group> groupID;
    private List<Integer> currentAvailability;
    private List<List<Boolean>> lists;
    

    public Calendar(ID<Group> id){

        this.groupID = id;

    }

    private void setCurrentAvailability(){
        this.currentAvailability = sumFewBooleanLists(this.lists);
    }

    public List<Integer> getCurrentAvailability(){
        return this.currentAvailability;
    }

    public void manualInitialization(){
        List<Boolean> a = new ArrayList<>();
        List<Boolean> b = new ArrayList<>();
        List<Boolean> c = new ArrayList<>();

        for (int i = 0; i < 5; i ++){
            a.add(Boolean.TRUE);
            b.add(Boolean.TRUE);
            c.add(Boolean.FALSE);
        }

        List<List<Boolean>> lists_local = new ArrayList<>();

        lists.add(a);
        lists.add(b);
        lists.add(c);

        this.lists = lists_local;

    }

    public static List<Integer> getSumOfTwoLists(List<Integer> first_list, List<Integer> second_list){

        int len = first_list.size();
        List<Integer> result = new ArrayList<>(len);

        for (int i = 0; i < len; i++){
            int element = first_list.get(i) + second_list.get(i);
            result.add(element);
        }
        return result;
    }

    public static List<Integer> getIntegerListFromBooleanList(List<Boolean> list){

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

    /*private List<List<Boolean> getAvailabilityListsFromFB (){
        //get from db
        //this.setCurrentAvailability();
    }*/
    
    public List<Integer> sumFewBooleanLists(List<List<Boolean>> lists){

        int lists_len = lists.size();
        int one_list_len = lists.get(0).size();

        List<Integer> result = new ArrayList<>(one_list_len);

        for (int i = 0; i < one_list_len; i++){
            result.add(0);
        }

        for (int j = 0; j < lists_len; j++){
            List<Integer> current_list = getIntegerListFromBooleanList(lists.get(j));
            result = getSumOfTwoLists(result, current_list);
        }

        return result;
    }
}
