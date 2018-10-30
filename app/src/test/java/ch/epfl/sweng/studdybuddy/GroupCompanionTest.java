package ch.epfl.sweng.studdybuddy;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.junit.Test;

import java.util.Arrays;

import ch.epfl.sweng.studdybuddy.core.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GroupCompanionTest {

    @Test
    public void getGroupSizeStableWhenNoUser() {
        DatabaseReference testref = mock(DatabaseReference.class);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList());
        when(dataSnapshot.getValue(User.class)).thenReturn(null);

    }
}
