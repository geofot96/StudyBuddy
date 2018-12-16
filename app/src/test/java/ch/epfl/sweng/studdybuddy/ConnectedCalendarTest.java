package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;
import ch.epfl.sweng.studdybuddy.tools.Observer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConnectedCalendarTest {
    ConnectedCalendar calendar;
    List<Boolean> list1 = new ArrayList<>();
    List<Boolean> list2 = new ArrayList<>();
    List<Boolean> list3 = new ArrayList<>();

    List<Integer> expectedList = new ArrayList<>();
    Map<String, List<Boolean>> hashMap = new HashMap<>();
    Map<String, List<Boolean>> emptyHashMap = new HashMap<>();

    Observer observer = mock(Observer.class);

    FirebaseReference firebaseReference = mock(FirebaseReference.class);
    ConnectedCalendar mockedCalendar = mock(ConnectedCalendar.class);
    DataSnapshot dataSnapshot = mock(DataSnapshot.class);

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

        when(firebaseReference.select(anyString())).thenReturn(firebaseReference);
        when(dataSnapshot.getKey()).thenReturn("");
    }

    @Test
    public void GetCorrectComputedAvailabilities(){
        calendar = new ConnectedCalendar(hashMap);
        assertEquals(expectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void AddingAUser(){
        calendar = new ConnectedCalendar(hashMap);
        calendar.modify("user3", list3);
        List<Integer> newExcpectedList = new ArrayList<>(expectedList);
        newExcpectedList.set(0,3);
        assertEquals(newExcpectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void modifyWithWrongInputDoNothing(){
        calendar = new ConnectedCalendar(hashMap);
        calendar.modify(null, list3);
        calendar.modify("user3", null);
        assertEquals(expectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void removingUser(){
        calendar = new ConnectedCalendar(hashMap);
        List<Integer> newExcpectedList = new ArrayList<>(expectedList);
        newExcpectedList.set(0,1);
        newExcpectedList.set(1,0);
        calendar.removeUser("user1");
        assertEquals(newExcpectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void removingWithWrongIDDoNothing(){
        calendar = new ConnectedCalendar(hashMap);
        calendar.removeUser(null);
        calendar.removeUser("user3");
        assertEquals(expectedList, calendar.getComputedAvailabilities());
    }

    @Test
    public void GetEmptyComputedAvailabilities(){
        calendar = new ConnectedCalendar(emptyHashMap);
        assertEquals(new ArrayList<>(), calendar.getComputedAvailabilities());
    }

    @Test
    public void addObserverAndNotifyItTest(){
        calendar = new ConnectedCalendar(hashMap);
        calendar.addObserver(observer);
        calendar.notifyObservers();
        verify(observer, times(1)).update(calendar);
    }

    @Test
    public void addNullAsObserverShouldThrowAnException(){
        calendar = new ConnectedCalendar(hashMap);
        try{
            calendar.addObserver(null);
        }catch (IllegalArgumentException e){
            return;
        }
        fail("Should have thrown an IllegalArgumentException");
    }

    @Test
    public void onChildChangedOnFirebaseTest(){
        calendar = new ConnectedCalendar(hashMap);
        calendar.calendarEventListener(mockedCalendar, firebaseReference).onChildChanged(dataSnapshot, "");
        verify(dataSnapshot, times(1)).getKey();
        verify(firebaseReference, times(1)).getAll(any(), any());
    }

    @Test
    public void onChildAddedOnFirebaseTest(){
        calendar = new ConnectedCalendar(hashMap);
        calendar.calendarEventListener(mockedCalendar, firebaseReference).onChildAdded(dataSnapshot, "");
        verify(dataSnapshot, times(1)).getKey();
        verify(firebaseReference, times(1)).getAll(any(), any());
    }

    @Test
    public void onChildRemovedOnFirebaseTest(){
        calendar = new ConnectedCalendar(hashMap);
        calendar.calendarEventListener(mockedCalendar, firebaseReference).onChildRemoved(dataSnapshot);
        verify(dataSnapshot, times(1)).getKey();
        verify(mockedCalendar, times(1)).removeUser(anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCalendarWithNoObserver(){
        new ConnectedCalendar(null, new ID<>(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCalendarWithNoID(){
        new ConnectedCalendar(observer, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCalendarWithNullParameters(){
        new ConnectedCalendar(null, null);
    }
}

