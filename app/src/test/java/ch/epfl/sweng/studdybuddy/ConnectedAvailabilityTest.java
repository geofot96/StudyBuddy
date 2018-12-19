package ch.epfl.sweng.studdybuddy;

import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

import static ch.epfl.sweng.studdybuddy.AvailabilityTest.checkAllFalse;
import static ch.epfl.sweng.studdybuddy.AvailabilityTest.checkHeadTrueTailFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConnectedAvailabilityTest {
    int columnTest = 0;
    int rowTest = 0;
    ConnectedAvailability isAvailable;
    DatabaseReference ref = mock(DatabaseReference.class);

    @Before
    public void setUp(){
        when(ref.child(anyString())).thenReturn(ref);
        Availability A = new ConcreteAvailability();
        Availability Available = new ConcreteAvailability();
        isAvailable = new ConnectedAvailability(Available,ref);
        isAvailable.modifyAvailability(rowTest,columnTest);
    }

    @Test
    public void getUserAvailabilityTest(){
        List<Boolean> listChecker = new ArrayList<>();
        for(int i = 0; i< ConnectedCalendar.CALENDAR_SIZE; i++){
            listChecker.add(false);
        }
        listChecker.set(0, true);
        assertEquals(listChecker, isAvailable.getUserAvailabilities());
    }

    @Test
    public void addAvailabilityInParticularSlot(){
        ConnectedAvailability connectedAvailability = new ConnectedAvailability(new ConcreteAvailability(), ref);
        connectedAvailability.modifyAvailability(rowTest, columnTest);
        checkHeadTrueTailFalse(connectedAvailability);
    }

    @Test
    public void removeAvailabilityInParticularSlot(){
        isAvailable.modifyAvailability(rowTest, columnTest);
        checkAllFalse(isAvailable);
        isAvailable.modifyAvailability(rowTest, columnTest);
    }

    @Test
    public void onSuccessGetDataListener(){
        Consumer callback = mock(Consumer.class);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList(dataSnapshot));
        when(dataSnapshot.getValue(Boolean.class)).thenReturn(true);
        isAvailable.availabilityGetDataListener(callback).onSuccess(dataSnapshot);
        verify(dataSnapshot, times(1)).getChildren();
        verify(dataSnapshot, times(1)).getValue(Boolean.class);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void negativeRow(){
        isAvailable.modifyAvailability(-1, 0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void negativeColumn(){isAvailable.modifyAvailability(0, -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void rowOutOfBound(){
        isAvailable.modifyAvailability(11,0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void columnOutOfBound(){ isAvailable.modifyAvailability(0,11);
    }

    @Test(expected = IllegalArgumentException.class)
    public void SecondConstructorWithNullDatabaseReference(){
        new ConnectedAvailability(new ConcreteAvailability(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void SecondConstructorWithNullAvailability(){
        new ConnectedAvailability(null, ref);
    }

    public void checkAllFalse(Availability list){
        int L = ConcreteAvailability.rowsNum;
        int l = ConcreteAvailability.columnsNum;
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < l; j++) {
                assertEquals(false, list.isAvailable(i,j));
            }
        }
    }
    public void checkHeadTrueTailFalse(Availability list){
        int L = ConcreteAvailability.rowsNum;
        int l = ConcreteAvailability.columnsNum;
        assertEquals(true, list.isAvailable(rowTest, columnTest));
        for (int i = 0; i < L; i++){
            checkTheRow(list,l, i);
        }
    }

    private void checkTheRow(Availability list, int l, int i) {
        for(int j = 0; j < l; j++){
            if(i!=rowTest || j!= columnTest){
                assertEquals(false, list.isAvailable(i,j));
            }
        }
    }


}

