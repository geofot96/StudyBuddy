package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DatabaseReference;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.util.Helper;

import static ch.epfl.sweng.studdybuddy.MetaFactory.deepFBReference;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.blankGroupWId;
import static ch.epfl.sweng.studdybuddy.util.CoreFactory.randomMeeting;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MetaMeetingTest {
    private DatabaseReference testref = deepFBReference();
   @Test
   public void pushMeetingArguments() {
       ReferenceWrapper wb = mock(FirebaseReference.class);
       when(wb.select(any())).thenReturn(wb);
       MetaMeeting mm = new MetaMeeting(wb);
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
}
