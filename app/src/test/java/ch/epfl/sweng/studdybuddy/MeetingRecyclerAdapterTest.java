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
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.util.CoreFactory;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MeetingRecyclerAdapterTest {
    List<Meeting> meetingList;
    int month,day,hourS,minS, hourE, minE;
    ID<Group> groupID = new ID<>("test");
    ID<User> adminID = new ID<>("test");
    Context context = mock(Context.class);
    Activity activity = mock(Activity.class);
    StudyBuddy app = mock(StudyBuddy.class);
    User random = new User("", new ID<>(""));

    TextView t = mock(TextView.class);
    CardView c = mock(CardView.class);
    View v = mock(View.class);
    MeetingRecyclerAdapter.ViewHolder h ;


    @Before
    public void setUp(){
        Meeting meeting = new Meeting();
        MeetingLocation mL = mock(MeetingLocation.class);
        when(mL.getTitle()).thenReturn("test");
        when(mL.getAddress()).thenReturn("");
        month = meeting.getStarting().getMonth()+1;
        day = meeting.getStarting().getDate();
        hourS = meeting.getStarting().getHours();
        minS = meeting.getStarting().getMinutes();
        hourE = meeting.getEnding().getHours();
        minE = meeting.getEnding().getMinutes();
        meeting.setLocation(mL);
        meetingList = Arrays.asList(meeting);

        when(activity.getApplication()).thenReturn(app);


        h = new MeetingRecyclerAdapter.ViewHolder(v, t, t, c);
    }

    @Test
    public void MeetingsCountTest(){
        MeetingRecyclerAdapter ad = new MeetingRecyclerAdapter(context, activity, new ArrayList<>(), groupID);
        MeetingRecyclerAdapter ad2 = new MeetingRecyclerAdapter(context, activity, meetingList, groupID);
        assertTrue(ad.getItemCount() == 0);
        assertTrue(ad2.getItemCount() == 1);
    }

    @Test
    public void onBindViewHolderWithNotClickable() {
        when(app.getAuthendifiedUser()).thenReturn(random);
        MeetingRecyclerAdapter adapter = new MeetingRecyclerAdapter(context, activity, meetingList, groupID);
        adapter.onBindViewHolder(h, 0);
        verify(t, times(1)).setText(
                month + "/" + day + " From: " + hourS +
                        ":" + minS/10 + minS%10 + " To: "+
                        hourE + ":" + minE/10 +minE%10);
        verify(t, times(1)).setText("test: ");
    }

}
