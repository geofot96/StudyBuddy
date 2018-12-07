package ch.epfl.sweng.studdybuddy;

import android.view.View;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.Resultable;

import static ch.epfl.sweng.studdybuddy.controllers.GroupController.leaveOnClick;
import static ch.epfl.sweng.studdybuddy.controllers.GroupController.processResult;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static android.app.Activity.RESULT_OK;

public class GroupControllerTest {

    Resultable res = mock(Resultable.class);
    @Test
    public void testLeaveOnClick() {
        MetaGroupAdmin mga = mock(MetaGroupAdmin.class);
        String uId = "123";
        Group group = blankGroupWId("123");
        Intentable destination = mock(Intentable.class);
        leaveOnClick(mga, uId, group, destination).onClick(mock(View.class));
        verify(mga, times(1)).clearListeners();
        verify(mga, times(1)).removeUserFromGroup(uId, group);
        verify(destination, times(1)).launch();
    }

    @Test
    public void testProcessResultInvalidResultCode() {
        processResult(1, -1111, res);
        verify(res, times(0)).onResult();
    }

    @Test
    public void testProcessResultInvalidRequestCode() {
        processResult(-1, RESULT_OK, res);
        verify(res, times(0)).onResult();
    }

    @Test
    public void testProcessResultValid() {
        processResult(1, RESULT_OK, res);
        verify(res, times(1)).onResult();
    }
}
