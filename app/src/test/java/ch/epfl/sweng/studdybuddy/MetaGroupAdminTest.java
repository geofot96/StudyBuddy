package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;

import static ch.epfl.sweng.studdybuddy.MetaFactory.deepFBReference;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.userGroup1;
import static org.junit.Assert.assertEquals;

public class MetaGroupAdminTest {

    private MetaGroup mg = new MetaGroup(new FirebaseReference(deepFBReference()));
    private List<Pair> userGroup = userGroup1();
    private Group ghostGroup = blankGroupWId("?");

    @Test
    public void rotateAdmin() {

    }

    @Test
    public void findAdminReturnsNullIfNoUserLeft() {

    }

    @Test
    public void findAdminReturnsUpdatedGroup() {

    }

    @Test
    public void findAdminStableIfGroupNull() {
        assertEquals(null, mg.findNextAdmin(null, userGroup.iterator()));
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
    public void leaveGroup() {

    }
}
