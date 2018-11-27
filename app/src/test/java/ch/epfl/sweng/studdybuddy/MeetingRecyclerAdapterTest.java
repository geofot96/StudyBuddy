package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
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
    int month,day,hourS,minS, hourE, minE;
    String testString = Messages.TEST;
    Context context = mock(Context.class);
    Activity activity = mock(Activity.class);
    StudyBuddy app = mock(StudyBuddy.class);
    User random = new User("", new ID<>(""));

    TextView t = mock(TextView.class);
    CardView cMock = mock(CardView.class);
    View v = mock(View.class);
    MeetingRecyclerAdapter.ViewHolder h ;
    MeetingRecyclerAdapter.ViewHolder viewHolder ;


    @Before
    public void setUp(){
        Meeting meeting = new Meeting();
        MeetingLocation mL = mock(MeetingLocation.class);
        when(mL.getTitle()).thenReturn("test");
        when(mL.getAddress()).thenReturn("");
        Date starting = new Date(meeting.getStarting());
        Date ending = new Date(meeting.getEnding());
        Calendar c = Calendar.getInstance();
        c.setTime(starting);
        month = c.get(Calendar.MONTH) +1;
        day = c.get(Calendar.DAY_OF_MONTH);
        hourS = c.get(Calendar.HOUR_OF_DAY);
        minS = c.get(Calendar.MINUTE);
        c.setTime(ending);
        hourE = c.get(Calendar.HOUR_OF_DAY);
        minE = c.get(Calendar.MINUTE);
        meeting.setLocation(mL);
        meetingList = Arrays.asList(meeting);

        when(activity.getApplication()).thenReturn(app);
        when(app.getAuthendifiedUser()).thenReturn(random);

        h = new MeetingRecyclerAdapter.ViewHolder(v, t, t, cMock);
    }

    @Test
    public void MeetingsCountTest(){
        MeetingRecyclerAdapter ad = new MeetingRecyclerAdapter(context, activity, new ArrayList<>(), new Pair(testString, testString));
        MeetingRecyclerAdapter ad2 = new MeetingRecyclerAdapter(context, activity, meetingList, new Pair(testString, testString));
        assertTrue(ad.getItemCount() == 0);
        assertTrue(ad2.getItemCount() == 1);
    }

    @Test
    public void onBindViewHolder() {
        MeetingRecyclerAdapter adapter = new MeetingRecyclerAdapter(context, activity, meetingList, new Pair(testString,testString));
        adapter.onBindViewHolder(h, 0);
        verify(t, times(1)).setText(
                month + "/" + day + " From: " + hourS +
                        ":" + minS/10 + minS%10 + " To: "+
                        hourE + ":" + minE/10 +minE%10);
        verify(t, times(1)).setText("test: ");
    }

}
