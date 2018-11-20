package ch.epfl.sweng.studdybuddy;

import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingRecyclerAdapter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MeetingRecyclerAdapterTest {
    List<Meeting> meetingList;
    int month,day,hourS,minS, hourE, minE;

    @Before
    public void setUp(){
        Meeting meeting = new Meeting();
        MeetingLocation mL = mock(MeetingLocation.class);
        when(mL.getTitle()).thenReturn("test");
        month = meeting.getStarting().getMonth()+1;
        day = meeting.getStarting().getDate();
        hourS = meeting.getStarting().getHours();
        minS = meeting.getStarting().getMinutes();
        hourE = meeting.getEnding().getHours();
        minE = meeting.getEnding().getMinutes();
        meeting.setLocation(mL);
        meetingList = Arrays.asList(meeting);
    }

    @Test
    public void MeetingsCountTest(){
        MeetingRecyclerAdapter ad = new MeetingRecyclerAdapter(new ArrayList<>());
        MeetingRecyclerAdapter ad2 = new MeetingRecyclerAdapter(meetingList);
        assertTrue(ad.getItemCount() == 0);
        assertTrue(ad2.getItemCount() == 1);
    }

    @Test
    public void partAdBinCallsBind() {
        TextView t = mock(TextView.class);
        View v = mock(View.class);
        MeetingRecyclerAdapter.ViewHolder h = new MeetingRecyclerAdapter.ViewHolder(v,t, t);
        MeetingRecyclerAdapter adapter = new MeetingRecyclerAdapter(meetingList);
        adapter.onBindViewHolder(h, 0);
        verify(t, times(1)).setText(
                                                    month + "/" + day + " From: " + hourS +
                                                    ":" + minS/10 + minS%10 + " To: "+
                                                    hourE + ":" + minE/10 +minE%10);
        verify(t, times(1)).setText("test");
    }


}
