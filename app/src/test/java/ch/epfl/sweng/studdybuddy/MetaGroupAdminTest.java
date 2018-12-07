package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.MetaFactory.deepFBReference;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.userGroup1;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.withAdmin;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MetaGroupAdminTest {

    private DatabaseReference testref = deepFBReference();
    private MetaGroupAdmin mg = new MetaGroupAdmin(new FirebaseReference(testref));
    private Iterator<Pair> userGroup = userGroup1().iterator();
    private Group ghostGroup = blankGroupWId("?");
    private Group group2 = blankGroupWId("123");
    private DataSnapshot groups = mock(DataSnapshot.class);

    @Before
    public void setup() {
        when(groups.getValue()).thenReturn(userGroup1());
        when(groups.child(any())).thenReturn(groups);
        when(groups.getChildren()).thenReturn(new ArrayList<>());
    }

    @Test
    public void rotateAdminCallsClearIfGroupHasNoUser() {
        mg.rotateAdmin(ghostGroup).onDataChange(groups);
        verify(testref, times(1)).child(Messages.FirebaseNode.USERGROUP);
        verify(testref, times(1)).child(Messages.FirebaseNode.GROUPS);
    }

    @Test
    public void rotateAdminCallsSetIfGroupHasUser() {

    }

    @Test
    public void findAdminReturnsNullIfNoUserLeft() {
        assertEquals(null, mg.findNextAdmin(ghostGroup, userGroup));
    }

    @Test
    public void findAdminReturnsUpdatedGroup() {
        String previousAdminID = group2.getAdminID().toString();
        Group updated = mg.findNextAdmin(group2, userGroup);
        assertEquals("456", updated.getAdminID());
        assertEquals(previousAdminID, group2.getAdminID().toString()); //Original group left unchanged
    }

    @Test
    public void findAdminStableIfGroupNull() {
        assertEquals(null, mg.findNextAdmin(null, userGroup));
    }

    @Test
    public void findAdminStableIfIteratorEmpty() {
        assertEquals(null, mg.findNextAdmin(ghostGroup, new ArrayList<Pair>().iterator()));
    }

    @Test
    public void findAdminStableIfIteratorNull() {
        assertEquals(null, mg.findNextAdmin(ghostGroup, null));
    }

    @Test
    public void removeUserFromGroupReturnsNull() {
        assertEquals(null, mg.removeUserFromGroup("notingroup", ghostGroup));
        verify(testref, times(1)).child(Messages.FirebaseNode.USERGROUP);
    }
    @Test public void removeUserFromGroupDoesNotReturnNull() {
        assertNotNull(mg.removeUserFromGroup("123", withAdmin("123")));
    }
    @Test
    public void putAll() {
        List<String> courses = Arrays.asList("1", "2", "3");
        mg.putAllCourses(courses, "a");
        verify(testref, times(3)).child("userCourse");
    }

    @Test
    public void onGroupGetNull() {

    }

    @Test
    public void onGroupGet() {

    }
}
