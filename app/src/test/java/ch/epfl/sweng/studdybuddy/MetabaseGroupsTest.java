package ch.epfl.sweng.studdybuddy;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetabaseGroupsTest {
    DatabaseReference testref = mock(DatabaseReference.class);
    DataSnapshot dataSnapshot = mock(DataSnapshot.class);
    List<Pair> tuples = Arrays.asList(new Pair("456", "123"), new Pair("789", "123"), new Pair("ghi", "abc"));
    DataSnapshot insaneSnapshot = mock(DataSnapshot.class);
    List<Pair> doublons = Arrays.asList(new Pair("k", "v"), new Pair("k", "v"), new Pair("k", "vv"), new Pair("kk", "v"));
    DataSnapshot groupTbl = mock(DataSnapshot.class);
    List<Group> gs = Arrays.asList(blankGroupWId("123"), blankGroupWId("abc"), blankGroupWId("v"), blankGroupWId("vv"));
    Metabase mb = new Metabase(new FirebaseReference(testref));

    private Group blankGroupWId(String id) {
        return new Group(1, new Course(""), "", id);
    }

    @Before public void setup() {
        DataSnapshot a = mock(DataSnapshot.class), b = mock(DataSnapshot.class), c = mock(DataSnapshot.class);
        List<DataSnapshot> snaps = Arrays.asList(a, b, c);
        for(int i = 0; i < snaps.size(); ++i) {
            when(snaps.get(i).getValue(Pair.class)).thenReturn(tuples.get(i));
        }
        when(dataSnapshot.getValue(Pair.class)).thenReturn(null);
        when(dataSnapshot.getChildren()).thenReturn(snaps);
        DataSnapshot s1 = mock(DataSnapshot.class), s2 = mock(DataSnapshot.class), s3 = mock(DataSnapshot.class), s4 = mock(DataSnapshot.class);
        List<DataSnapshot> insaneSnaps = Arrays.asList(s1, s2, s3, s4);
        for(int i = 0; i < doublons.size(); ++i) {
            when(insaneSnaps.get(i).getValue(Pair.class)).thenReturn(doublons.get(i));
        }
        when(insaneSnapshot.getValue(Pair.class)).thenReturn(null);
        when(insaneSnapshot.getChildren()).thenReturn(insaneSnaps);

        List<DataSnapshot> groups = Arrays.asList(mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class), mock(DataSnapshot.class));
        for(int i = 0; i < groups.size(); ++i) {
            when(groups.get(i).getValue(Group.class)).thenReturn(gs.get(i));
        }
        when(groupTbl.getValue(Group.class)).thenReturn(null);
        when(groupTbl.getChildren()).thenReturn(groups);


    }
    @Test
    public void getAllGroupSizesWorksWhenNoGroup() {
        FirebaseReference ref = new FirebaseReference(testref);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        when(dataSnapshot.getValue(Pair.class)).thenReturn(null);
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList());
        assertNotNull(mb.getReference());
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

    @Test
    public void getUserGroupsWhenDirtyDB() {
        List<Group> userGroups = new ArrayList<>();
        DataSnapshot brokenTbl = mock(DataSnapshot.class);
        when(brokenTbl.getValue(Group.class)).thenReturn(null);
        when(brokenTbl.getChildren()).thenReturn(null);
        List<String> gIds = new ArrayList<>();
        mb.getUserGroups("k", gIds, userGroups).onDataChange(brokenTbl);
        mb.getGroupsfromIds(gIds, userGroups);
        assertTrue(userGroups.size() == 0);
    }
}
