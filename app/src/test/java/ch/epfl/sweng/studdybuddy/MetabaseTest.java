package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Buddy;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;


import ch.epfl.sweng.studdybuddy.tools.Intentable;

import ch.epfl.sweng.studdybuddy.util.Messages;


import ch.epfl.sweng.studdybuddy.util.Messages;


import static ch.epfl.sweng.studdybuddy.util.CoreFactory.johnDoe;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MetabaseTest {

    DataSnapshot userCourses = mock(DataSnapshot.class);
    DatabaseReference testref = mock(DatabaseReference.class, RETURNS_SELF);
    FirebaseReference fr = new FirebaseReference(testref);
    MetaGroup mb = new MetaGroup(fr);

    @Before
    public void setup() {
        List<DataSnapshot> ucs = Arrays.asList(mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class));
        List<Pair> userCourse = Arrays.asList(new Pair("Robert", "cuisine"), new Pair("Albert", "royalty"), new Pair("Frédé", "clp"), new Pair("Frédé", "parcon"), new Pair("Frédé", "parcon"));
        for(int i = 0; i < ucs.size(); ++i)
            when(ucs.get(i).getValue(Pair.class)).thenReturn(userCourse.get(i));
        when(userCourses.getChildren()).thenReturn(ucs);
        when(testref.child(anyString())).thenReturn(testref);
    }
    @Test
    public void getUserCoursesInvalidUser() {
        List<String> travisCourse = new ArrayList<>();
        mb.getUserCourses("Travis", travisCourse).onDataChange(userCourses);
        assertTrue(travisCourse.size() == 0);
    }

    @Test
    public void getUserCoursesWhenDoublon() {
        List<String> fredeCourses = new ArrayList<>();
        mb.getUserCourses("Frédé", fredeCourses).onDataChange(userCourses);
        assertTrue(fredeCourses.size() == 2);
        assertTrue(fredeCourses.contains("clp"));
        assertTrue(fredeCourses.contains("parcon"));
    }

    @Test
    public void getUserAndConsumeTest(){
        DataSnapshot ds = mock(DataSnapshot.class);
        User user = new User("Tintin", "Milou");
        when(ds.getValue()). thenReturn(user);
        mb.getUserAndConsume("Bouba", new Consumer<User>() {
            @Override
            public void accept(User user) {
                assertTrue(user.getName().equals("Tintin")) ;
            }
        }).onDataChange(ds);
    }


    private static Pair p(String a, String b) {
        return new Pair(a, b);
    }

    @Test
    public void testUpdateCourses() {
        AdapterAdapter ad = mock(AdapterAdapter.class);
        mb.addListenner(ad);
        List<Pair> pairs = Arrays.asList(p("1", "2"), p("1", "1"), p("2", "1"));
        List<DataSnapshot> dss = Arrays.asList(mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class));
        Intentable dest = mock(Intentable.class);
        DataSnapshot ds = mock(DataSnapshot.class);
        when(ds.getChildren()).thenReturn(dss);
        for (int i = 0; i < 3; ++i) {
            when(dss.get(i).getValue(Pair.class)).thenReturn(pairs.get(i));
        }
        mb.updateUserCourses("1", Arrays.asList("2"), dest).onDataChange(ds);
        verify(testref, times(1)).removeEventListener(any(ValueEventListener.class));
        verify(dest, times(1)).launch();
        //The only remaining course to put
        verify(testref, times(1)).setValue(any(Pair.class));
        //The element to delete
        verify(testref, times(1)).removeValue();
    }

    @Test
    public void testGetBuddies() {
        List<Buddy> allBuddies = Arrays.asList(buddy("bob"), buddy("eve"), new Buddy("bob", "eve"));
        List<User> buddies = new ArrayList<>();
        DataSnapshot root = mock(DataSnapshot.class);
        List<DataSnapshot> dsBuds = Arrays.asList(mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class));
        for(int i = 0; i < allBuddies.size(); ++i) {
            when(dsBuds.get(i).getValue(Buddy.class)).thenReturn(allBuddies.get(i));
        }
        when(root.getChildren()).thenReturn(dsBuds);
        mb.getBuddies("alice", buddies).onDataChange(root);
        verify(testref, times(2)).addValueEventListener(any(ValueEventListener.class));
        verify(testref, times(1)).child(Messages.FirebaseNode.BUDDIES);
        verify(testref, times(1)).child("users");
    }

    @Test
    public void testFetchUsers() {
        List<User> users = Arrays.asList(johnDoe("1"));
        List<String> usernames = new ArrayList<>();
        usernames.add("Robert");
        usernames.add("Kim");
        usernames.add("Khloe");
        DataSnapshot user = mock(DataSnapshot.class);
        DataSnapshot user1 = mock(DataSnapshot.class);
        when(user1.getValue(User.class)).thenReturn(users.get(0));
        when(user.getChildren()).thenReturn(Arrays.asList(user1));
        AdapterAdapter ad = mock(AdapterAdapter.class);
        //mb.addListenner(ad); will fail getBuddies
        mb.fetchUserNames(usernames).onDataChange(user);
        assertEquals(1, usernames.size());
        assertEquals("John Doe", usernames.get(0));
        //verify(ad, times(1)).notify();
    }

    private static Buddy buddy(String friend) {
        return new Buddy("alice", friend);
    }
}
