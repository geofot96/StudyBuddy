package ch.epfl.sweng.studdybuddy;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.util.Helper;

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
    ReferenceWrapper wb = mock(FirebaseReference.class);
    MetaMeeting mm = new MetaMeeting(wb);
    @Before
    public void setup() {
        when(wb.select(any())).thenReturn(wb);

    }
   @Test
   public void pushMeetingArguments() {
       Meeting m = randomMeeting();
       String mid = m.getId().getId();
       Group bg = blankGroupWId("-");
       mm.pushMeeting(m, bg);
       Pair binding = new Pair(mid, bg.getGroupID().getId());
       when(testref.child(any())).thenReturn(testref);
       verify(wb, times(1)).select("meetings");
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
}
