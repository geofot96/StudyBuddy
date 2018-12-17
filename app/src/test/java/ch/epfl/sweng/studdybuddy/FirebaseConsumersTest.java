package ch.epfl.sweng.studdybuddy;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseConsumers;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

public class FirebaseConsumersTest {

    List<User> users ;
    MetaGroup metaGroup;


    @Before
    public void setup(){
        new FirebaseConsumers();
        users = Arrays.asList(new User("a","b"), new User("c","d"));
        metaGroup = mock(MetaGroup.class);
    }
    @Test
    public void filterBuddiesWithParticipantsTest(){
        List<User> buddies = new ArrayList<>();
        buddies.add(users.get(0));
        buddies.add(new User("e","f") );
        FirebaseConsumers.filterBuddiesWithParticipants(buddies, metaGroup).accept(users);
        assertTrue(!buddies.contains(users.get(0)));
        assertTrue(buddies.size() ==   1);
    }

    @Test
    public void filterBuddiesTest(){
        List<String> uIds = new ArrayList<>();
        FirebaseConsumers.filterBuddies("11", uIds, users, metaGroup).accept(users);
        assertTrue(uIds.size() == users.size());
        for(int i = 0; i < users.size(); i++){
            assertTrue(uIds.contains(users.get(i).getUserID().getId()));
        }
    }
}
