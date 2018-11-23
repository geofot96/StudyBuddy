package ch.epfl.sweng.studdybuddy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;

import static org.junit.Assert.assertEquals;

public class ConnectedCalendartest {
    ConnectedCalendar calendar;
    List<Boolean> list1 = new ArrayList<>();
    List<Boolean> list2 = new ArrayList<>();
    List<Boolean> list3 = new ArrayList<>();

    List<Integer> expectedList = new ArrayList<>();
    HashMap<String, List<Boolean>> hashMap = new HashMap<>();
    HashMap<String, List<Boolean>> emptyHashMap = new HashMap<>();
    ID<Group> groupID = new ID<>("group");

    @Before
    public void setUp(){

        for (int i = 0; i < 10; i++){
            list1.add(false);
            list2.add(false);
            list3.add(false);
            expectedList.add(0);
        }
        list1.set(0, true);
        list2.set(0, true);
        list3.set(0, true);
        list1.set(1, true);
        list2.set(2, true);
        expectedList.set(0, 2);
        expectedList.set(1, 1);
        expectedList.set(2, 1);
        hashMap.put("user1", list1);
        hashMap.put("user2", list2);

    }

    @Test
    public void GetCorrectComputedAvailabilities(){
        calendar = new ConnectedCalendar(groupID, hashMap);
        assertEquals(expectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void AddingAUser(){
        calendar = new ConnectedCalendar(groupID, hashMap);
        calendar.modify("user3", list3);
        List<Integer> newExcpectedList = new ArrayList<>(expectedList);
        newExcpectedList.set(0,3);
        assertEquals(newExcpectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void modifyWithWrongInputDoNothing(){
        calendar = new ConnectedCalendar(groupID, hashMap);
        calendar.modify(null, list3);
        calendar.modify("user3", null);
        assertEquals(expectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void removingUser(){
        calendar = new ConnectedCalendar(groupID, hashMap);
        List<Integer> newExcpectedList = new ArrayList<>(expectedList);
        newExcpectedList.set(0,1);
        newExcpectedList.set(1,0);
        calendar.removeUser("user1");
        assertEquals(newExcpectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void removingWithWrongIDDoNothing(){
        calendar = new ConnectedCalendar(groupID, hashMap);
        calendar.removeUser(null);
        calendar.removeUser("user3");
        assertEquals(expectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void GetEmptyComputedAvailabilities(){
        calendar = new ConnectedCalendar(groupID, emptyHashMap);
        assertEquals(new ArrayList<>(), calendar.getComputedAvailabilities());
    }
}
