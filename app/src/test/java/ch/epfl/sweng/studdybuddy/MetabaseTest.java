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
import ch.epfl.sweng.studdybuddy.firebase.Metabase;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

import ch.epfl.sweng.studdybuddy.tools.Intentable;

import ch.epfl.sweng.studdybuddy.util.Messages;


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


    @Test
    public void testBefriend() {
        Buddy b = new Buddy("alice", "bob");
        mb.befriend("alice", "bob");
        verify(testref, times(1)).child(Messages.FirebaseNode.BUDDIES);
        verify(testref, times(1)).child(b.hash());
        verify(testref, times(1)).setValue(any(Buddy.class));
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

    private static Buddy buddy(String friend) {
        return new Buddy("alice", friend);

    }
}
