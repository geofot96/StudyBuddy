package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.tools.Intentable;

import static ch.epfl.sweng.studdybuddy.controllers.CreateGroupController.joinGroupsAndGo;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.johnDoe;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CreateGroupControllerTest {
    @Test
    public void testjoinGroupsAndGo() {
        MetaGroup mg = mock(MetaGroup.class);
        Group g = blankGroupWId("123");
        User u = johnDoe("1");
        Intentable dest = mock(Intentable.class);
        /*joinGroupsAndGo(mg, u, g, dest);
        verify(mg, times(1)).pushGroup(g, u.getUserID().getId());
        verify(dest, times(1)).launch();*/
    }
}
