package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studdybuddy.core.Buddy;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.MetaFactory.deepFBReference;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.groups1;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.userGroup1;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.users1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MetabaseGroupsTest {
    DatabaseReference testref = deepFBReference();
    DataSnapshot dataSnapshot = mock(DataSnapshot.class);
    List<Pair> tuples = userGroup1();
    DataSnapshot insaneSnapshot = mock(DataSnapshot.class);
    List<Pair> doublons = Arrays.asList(new Pair("k", "v"), new Pair("k", "v"), new Pair("k", "vv"), new Pair("kk", "v"));
    DataSnapshot groupTbl = mock(DataSnapshot.class);
    List<Group> gs = groups1();
    MetaGroup mb = new MetaGroup(new FirebaseReference(testref));
    List<User> usrs = users1();
    DataSnapshot usrTbl = mock(DataSnapshot.class);


    @Before public void setup() {
        DataSnapshot a = mock(DataSnapshot.class), b = mock(DataSnapshot.class), c = mock(DataSnapshot.class);
        List<DataSnapshot> snaps = Arrays.asList(a, b, c);
        fillSnapshots(snaps, tuples, Pair.class);
        when(dataSnapshot.getValue(Pair.class)).thenReturn(null);
        when(dataSnapshot.getChildren()).thenReturn(snaps);
        DataSnapshot s1 = mock(DataSnapshot.class), s2 = mock(DataSnapshot.class), s3 = mock(DataSnapshot.class), s4 = mock(DataSnapshot.class);
        List<DataSnapshot> insaneSnaps = Arrays.asList(s1, s2, s3, s4);
        fillSnapshots(insaneSnaps, doublons, Pair.class);
        when(insaneSnapshot.getValue(Pair.class)).thenReturn(null);
        when(insaneSnapshot.getChildren()).thenReturn(insaneSnaps);
        List<DataSnapshot> groups = Arrays.asList(mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class));
        fillSnapshots(groups, gs, Group.class);
        when(groupTbl.getValue(Group.class)).thenReturn(null);
        when(groupTbl.getChildren()).thenReturn(groups);

        List<DataSnapshot> usrSnaps = Arrays.asList(mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class));
        fillSnapshots(usrSnaps, usrs, User.class);
        when(usrTbl.getChildren()).thenReturn(usrSnaps);
    }

    private <T> void fillSnapshots(List<DataSnapshot> snapshots, List<T> values, Class<T> classT){
        for(int i = 0; i < snapshots.size(); ++i) {
            when(snapshots.get(i).getValue(classT)).thenReturn(values.get(i));
        }
    }
    @Test
    public void getAllGroupSizesWorksWhenNoGroup() {
        FirebaseReference ref = new FirebaseReference(testref);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        when(dataSnapshot.getValue(Pair.class)).thenReturn(null);
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList());
        Map<String, Integer> sizes = new HashMap<>();
        mb.getAllGroupSizes(sizes).onDataChange(dataSnapshot);
        assertEquals(0, sizes.size());
    }

    @Test
    public void getAllGroupSizesWorksWhen2Groups() {
        Map<String, Integer> sizes = new HashMap<>();
        mb.getAllGroupSizes(sizes).onDataChange(dataSnapshot);
        assertEquals(2, sizes.size());
        assertTrue(2 == sizes.get("123"));
        assertTrue(1 == sizes.get("abc"));
    }

    @Test
    public void getUserGroupsWhenInvalidUser() {
        List<Group> userGroups = new ArrayList<>();
        List<String> gIds = new ArrayList<>();
        mb.getUserGroups("noid", gIds, userGroups).onDataChange(dataSnapshot);
        mb.getGroupsfromIds(gIds, userGroups).onDataChange(groupTbl);
        assertEquals(0, userGroups.size());
    }

    @Test
    public void getUserGroupsWhenDoublon() {
        List<Group> userGroups = new ArrayList<>();
        List<String> gIds = new ArrayList<>();
        mb.getUserGroups("k", gIds, userGroups).onDataChange(insaneSnapshot);
        mb.getGroupsfromIds(gIds, userGroups).onDataChange(groupTbl);
        assertEquals(2, userGroups.size());
        List<String> presentIds = Arrays.asList("123", "abc", "v", "vv");
        for(Group uG : userGroups)
            assertTrue(presentIds.contains(uG.getGroupID().toString()));
    }

    //Actually tests fbref?!.#@*-
    @Test
    public void getUserGroupsWhenDirtyDB() {
        List<Group> userGroups = new ArrayList<>();
        DataSnapshot brokenTbl = mock(DataSnapshot.class);
        when(brokenTbl.getValue(Group.class)).thenReturn(null);
        when(brokenTbl.getChildren()).thenReturn(null);
        List<String> gIds = new ArrayList<>();
        mb.getUserGroups("k", gIds, userGroups).onDataChange(brokenTbl);
        mb.getGroupsfromIds(gIds, userGroups).onDataChange(groupTbl);

        assertTrue(userGroups.size() == 0);
    }

    @Test
    public void getUserGroupsWhenUnemptyInput() {
        List<Group> userGroups = new LinkedList<>();//Arrays.asList(blankGroupWId("bjdajkba"), blankGroupWId("bvdsjjd"));
        List<String> ids = new ArrayList<>();
        userGroups.add(blankGroupWId("bjdajkba"));
        userGroups.add(blankGroupWId("bvdsjjd"));
        mb.getUserGroups("ghi", ids, userGroups).onDataChange(dataSnapshot);
        mb.getGroupsfromIds(ids, userGroups).onDataChange(groupTbl);
        assertTrue(userGroups.size() == 1);
    }

    @Test
    public void getGroupUsersWhenDoublon() {
        List<User> groupUsers = new LinkedList<>();
        List<String> ids = new ArrayList<>();
        mb.getGroupUsers("v", ids, groupUsers).onDataChange(insaneSnapshot);
        assertEquals(2, ids.size());
        mb.getUsersfromIds(ids, groupUsers).onDataChange(usrTbl);
        assertEquals(2, groupUsers.size());
        //assertTrue(groupUsers.size() == 2);
    }

    @Test
    public void getGroupUsersWhenNoUser() {
        List<User> groupUsers = Arrays.asList();
        List<String> ids = new ArrayList<>();
        mb.getGroupUsers("jbbfesdbo", ids, groupUsers).onDataChange(insaneSnapshot);
        mb.getUsersfromIds(ids, groupUsers).onDataChange(usrTbl);
        assertTrue(groupUsers.size() == 0);
    }

    @Test
    public void pushGroupCallsWithWellFormedArgs() {
        when(testref.child(Messages.FirebaseNode.USERGROUP)).thenReturn(testref);
        when(testref.child(Messages.FirebaseNode.GROUPS)).thenReturn(testref);
        when(testref.setValue(any(Pair.class))).thenReturn(null);
        when(testref.setValue(any(Group.class))).thenReturn(null);
        mb.pushGroup(blankGroupWId("ab"), "123");
        ArgumentCaptor<String> p = ArgumentCaptor.forClass(String.class);
        verify(testref, times(4)).child(p.capture());
        assertTrue(p.getAllValues().contains("ab"));
        assertTrue(p.getAllValues().contains(Messages.FirebaseNode.GROUPS));
        assertTrue(p.getAllValues().contains(Messages.FirebaseNode.USERGROUP));
        assertTrue(p.getAllValues().contains(Helper.hashCode(new Pair("123", "ab"))));
    }

    @Test
    public void buddiesFilterTest(){
        when(testref.child(Messages.FirebaseNode.USERGROUP)).thenReturn(testref);
        when(testref.child(Messages.FirebaseNode.GROUPS)).thenReturn(testref);
        when(testref.child(Messages.FirebaseNode.BUDDIES)).thenReturn(testref);
        when(testref.child(Messages.FirebaseNode.USERS)).thenReturn(testref);
        List<User> participants = new ArrayList<>();
        List<String > uIds = new ArrayList<>();
        DataSnapshot snapList = mock(DataSnapshot.class);
        DataSnapshot snap = mock(DataSnapshot.class);

        Buddy buddy = new Buddy("a", "b");

        when(snapList.getChildren()).thenReturn(Arrays.asList(snap));
        when(snap.getValue(Buddy.class)).thenReturn(buddy);
        Consumer<List<User>> filterBuddies = mock(Consumer.class);
        mb.getBuddiesAndConsume("a", participants, uIds, filterBuddies ).onDataChange(snapList);
        assertTrue(uIds.contains("b"));
        assertFalse(uIds.contains("a"));
    }






}
