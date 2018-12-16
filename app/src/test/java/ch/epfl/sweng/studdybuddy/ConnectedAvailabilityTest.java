package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConnectedAvailabilityTest {
    int columnTest = 0;
    int rowTest = 0;

    ConnectedAvailability connectedA;
    ConnectedAvailability connectedAvailable;

    DatabaseReference ref = mock(DatabaseReference.class);

    @Before
    public void setUp(){
        when(ref.child(anyString())).thenReturn(ref);
        Availability A = new ConcreteAvailability();
        Availability Available = new ConcreteAvailability();
        connectedA = new ConnectedAvailability(A, ref);
        connectedAvailable = new ConnectedAvailability(Available,ref);
        connectedAvailable.modifyAvailability(rowTest,columnTest);
    }

    @Test
    public void addAvailabilityInParticularSlot(){
        connectedA.modifyAvailability(rowTest, columnTest);
        checkHeadTrueTailFalse(connectedA);
    }

    @Test
    public void removeAvailabilityInParticularSlot(){
        connectedAvailable.modifyAvailability(rowTest, columnTest);
        checkAllFalse(connectedAvailable);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void negativeRow(){
        connectedA.modifyAvailability(-1, 0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void negativeColumn(){
        connectedA.modifyAvailability(0, -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void rowOutOfBound(){
        connectedA.modifyAvailability(11,0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void columnOutOfBound(){ connectedA.modifyAvailability(0,11);
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
            for(int j = 0; j < l; j++){
                if(i!=rowTest || j!= columnTest){
                    assertEquals(false, list.isAvailable(i,j));
                }
            }
        }
    }
}

