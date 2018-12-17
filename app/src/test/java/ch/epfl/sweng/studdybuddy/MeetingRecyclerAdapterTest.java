package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.util.CoreFactory;
import ch.epfl.sweng.studdybuddy.util.DateTimeHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MeetingRecyclerAdapterTest {
    List<Meeting> meetingList;
    Bundle origin = mock(Bundle.class);
    Context context = mock(Context.class);
    Activity activity = mock(Activity.class);
    StudyBuddy app = mock(StudyBuddy.class);
    User random = new User("", new ID<>(""));

    TextView t = mock(TextView.class);
    CardView cMock = mock(CardView.class);
    View v = mock(View.class);
    MeetingRecyclerAdapter.ViewHolder h ;


    @Before
    public void setUp(){
        MeetingLocation mL = mock(MeetingLocation.class);
        when(mL.getTitle()).thenReturn("test");
        when(mL.getAddress()).thenReturn("");
        Meeting meeting = new Meeting((long)0,(long)0, mL, Messages.TEST);
        meetingList = Arrays.asList(meeting);
        when(activity.getApplication()).thenReturn(app);
        when(app.getAuthendifiedUser()).thenReturn(random);
        h = new MeetingRecyclerAdapter.ViewHolder(v, t, t, cMock);

        when(origin.getString(any())).thenReturn(Messages.TEST);
    }

    @Test
    public void MeetingsCountTest(){
        MeetingRecyclerAdapter ad = new MeetingRecyclerAdapter(context, activity, new ArrayList<>(), origin);
        MeetingRecyclerAdapter ad2 = new MeetingRecyclerAdapter(context, activity, meetingList, origin);
        assertTrue(ad.getItemCount() == 0);
        assertTrue(ad2.getItemCount() == 1);
    }

    @Test
    public void onBindViewHolder() {
        MeetingRecyclerAdapter adapter = new MeetingRecyclerAdapter(context, activity, meetingList, origin);
        adapter.onBindViewHolder(h, 0);
        verify(t, times(1)).setText(
                DateTimeHelper.printMeetingDate(0,0));
        verify(t, times(1)).setText("test: ");
    }

}
