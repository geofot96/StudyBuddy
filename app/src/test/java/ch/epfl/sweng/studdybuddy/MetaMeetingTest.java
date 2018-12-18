package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.MetaFactory.deepFBReference;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.randomMeeting;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MetaMeetingTest {
    private DatabaseReference testref = deepFBReference();

    private ReferenceWrapper wb;
    private MetaMeeting mm;
    private Meeting m;
    private Group bg;
    @Before
    public void setUp(){
        wb = mock(FirebaseReference.class);
        when(wb.select(any())).thenReturn(wb);
        mm = new MetaMeeting(wb);
        m = randomMeeting();
        bg = blankGroupWId("-");
        when(testref.child(any())).thenReturn(testref);
    }


    @Test
   public void pushMeetingArguments() {
       String mid = m.getId().getId();
       mm.pushMeeting(m, bg);
       when(testref.child(any())).thenReturn(testref);
       verify(wb, times(1)).select(Messages.FirebaseNode.MEETINGS);
       verify(wb, times(1)).select(bg.getGroupID().getId());
       verify(wb, times(1)).select(mid);
       verify(wb, times(1)).setVal(m);
   }

   @Test
    public void fetchMeetingsArguments() {
       DataSnapshot meetings = mock(DataSnapshot.class);
       when(meetings.getValue()).thenReturn(meetings);
       when(meetings.getChildren()).thenReturn(new ArrayList<>());
       ReferenceWrapper ref = new FirebaseReference(testref);
       MetaMeeting meta = new MetaMeeting(ref);
       meta.fetchMeetings("-", new Consumer<List<Meeting>>() {
           @Override
           public void accept(List<Meeting> meetings) {
               assertTrue(meetings.isEmpty());
           }
       }).onDataChange(meetings);
   }

   @Test
    public void deleteMeetingArgument() {
       mm.deleteMeeting(m.getId(), bg.getGroupID());
       verify(wb, times(1)).select(Messages.FirebaseNode.MEETINGS);
       verify(wb, times(1)).select(bg.getGroupID().getId());
       verify(wb, times(1)).select(m.getId().getId());
       verify(wb, times(1)).clear();
   }

     @Test
     public void pushMeetingArgumentsWithSecondMethod(){
         String mid = m.getId().getId();
         mm.pushMeeting(m, bg.getGroupID());
         when(testref.child(any())).thenReturn(testref);
         verify(wb, times(1)).select(Messages.FirebaseNode.MEETINGS);
         verify(wb, times(1)).select(bg.getGroupID().getId());
         verify(wb, times(1)).select(mid);
         verify(wb, times(1)).setVal(m);
     }

     @Test
    public void getMeetingsOfGroup(){
        Consumer<List<Meeting>> consumer = mock(Consumer.class);
        mm.getMeetingsOfGroup(bg.getGroupID(), consumer);
       verify(wb, times(1)).select("meetings");
       verify(wb, times(1)).select(bg.getGroupID().getId());
       verify(wb, times(1)).getAll(Meeting.class, consumer);
    }

    @Test
    public void pushLocationTest(){
        mm.pushLocation(m.getLocation(), bg.getGroupID(),m.getId());
        when(testref.child(any())).thenReturn(testref);
        verify(wb, times(1)).setVal(m.getLocation());
    }
}
