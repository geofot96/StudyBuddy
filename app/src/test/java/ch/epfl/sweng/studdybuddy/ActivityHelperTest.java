package ch.epfl.sweng.studdybuddy;

import android.widget.DatePicker;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;

import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.listenDate;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.randomMeeting;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ActivityHelperTest {
    @Test
    public void testListenDate() {
        Meeting mee = randomMeeting();
        Group group = blankGroupWId("?");
        MetaMeeting mm = mock(MetaMeeting.class);
        AdapterAdapter adapter = mock(AdapterAdapter.class);
        DatePicker view = mock(DatePicker.class);
        listenDate(mee, group, mm, adapter).onDateSet(view, 2018, 11, 5);
        verify(adapter, times(1)).update();
        verify(mm, times(1)).pushMeeting(any(), any(Group.class));
        //verify(mee).getDeadline().setMonth(11);
    }

}
