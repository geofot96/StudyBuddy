package ch.epfl.sweng.studdybuddy;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.junit.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;

import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.listenDate;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.listenTime;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.meetingConsumer;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.randomMeeting;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ActivityHelperTest {
    Meeting mee = randomMeeting();
    Group group = blankGroupWId("?");
    MetaMeeting mm = mock(MetaMeeting.class);
    AdapterAdapter adapter = mock(AdapterAdapter.class);
    DatePicker view = mock(DatePicker.class);
    TimePicker time = mock(TimePicker.class);
    TextView title = mock(TextView.class);
    Button timeB = mock(Button.class);
    Button date = mock(Button.class);
    Button plus = mock(Button.class);
    @Test
    public void testListenDate() {


        listenDate(mee, group, mm, adapter).onDateSet(view, 2018, 11, 5);
        verify(adapter, times(1)).update();
        verify(mm, times(1)).pushMeeting(any(), any(Group.class));
        //verify(mee).getDeadline().setMonth(11);
    }

    @Test
    public void testListenTime() {
        listenTime(mee, group, mm,adapter).onTimeSet(time, 0, 0);
        verify(adapter, times(1)).update();
        verify(mm, times(1)).pushMeeting(mee, group);
    }

    @Test
    public void testNoMeetingConsumer() {
        meetingConsumer(title, timeB, date, plus).accept(new ArrayList<>());
        verify(timeB).setVisibility(View.GONE);
        verify(date).setVisibility(View.GONE);
        verify(title).setVisibility(View.GONE);
    }

    @Test
    public void testMeetingConsumer() {
        meetingConsumer(title, timeB, date, plus).accept(Arrays.asList(mee));
        verify(timeB).setVisibility(View.VISIBLE);
        verify(date).setVisibility(View.VISIBLE);
        verify(title).setVisibility(View.VISIBLE);
        verify(date).setText(mee.date());
        verify(timeB).setText(mee.time());
        verify(plus).setVisibility(View.GONE);
    }

}
