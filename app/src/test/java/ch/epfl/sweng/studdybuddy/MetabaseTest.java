package ch.epfl.sweng.studdybuddy;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
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

public class MetabaseTest {
    @Test
    public void getAllGroupSizesWorksWhenNoGroup() {
        DatabaseReference testref = mock(DatabaseReference.class);
        FirebaseReference ref = new FirebaseReference(testref);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        when(dataSnapshot.getValue(Pair.class)).thenReturn(null);
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList());
        Metabase mb = new Metabase(ref);
        assertNotNull(mb.getReference());
        Map<String, Integer> sizes = new HashMap<>();
        mb.getAllGroupSizes(sizes).onDataChange(dataSnapshot);
        assertEquals(0, sizes.size());
    }

    @Test
    public void getAllGroupSizesWorksWhen2Groups() {
        DatabaseReference testref = mock(DatabaseReference.class);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        List<Pair> tuples = Arrays.asList(new Pair("123", "456"), new Pair("123", "789"), new Pair("abc", "ghi"));
        DataSnapshot a = mock(DataSnapshot.class), b = mock(DataSnapshot.class), c = mock(DataSnapshot.class);
        when(a.getValue()).thenReturn(tuples.get(0));
        when(b.getValue()).thenReturn(tuples.get(1));
        when(c.getValue()).thenReturn(tuples.get(2));
        when(dataSnapshot.getValue(Pair.class)).thenReturn(null);
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList(a, b, c));
        Map<String, Integer> sizes = new HashMap<>();
        Metabase mb = new Metabase(new FirebaseReference(testref));
        mb.getAllGroupSizes(sizes).onDataChange(dataSnapshot);
        assertEquals(2, sizes.size());
        assertTrue(2 == sizes.get("123"));
        assertTrue(1 == sizes.get("abc"));
    }
}
