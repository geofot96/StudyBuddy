package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.Intentable;

import static ch.epfl.sweng.studdybuddy.controllers.GroupController.leaveOnClick;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GroupControllerTest {
    @Test
    public void testLeaveOnClick() {
        MetaGroupAdmin mga = mock(MetaGroupAdmin.class);
        String uId = "123";
        Group group = blankGroupWId("123");
        Intentable destination = mock(Intentable.class);
        leaveOnClick(mga, uId, group, destination);
        verify(mga, times(1)).clearListeners();
        verify(mga, times(1)).removeUserFromGroup(uId, group);
        verify(destination, times(1)).launch();
    }
}
