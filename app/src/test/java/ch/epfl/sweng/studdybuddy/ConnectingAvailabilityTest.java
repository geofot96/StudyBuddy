package ch.epfl.sweng.studdybuddy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConnectedAvailabilityTest {
    int columnTest = 0;
    int rowTest = 0;

    ConnectedAvailability connectedA;
    ConnectedAvailability connectedAvailable;

    FirebaseReference ref = mock(FirebaseReference.class);

    @Before
    public void setUp(){
        when(ref.select(anyString())).thenReturn(ref);
        Availability A = new ConcreteAvailability();
        Availability Available = new ConcreteAvailability();
        connectedA = new ConnectedAvailability("","",A, ref);
        connectedAvailable = new ConnectedAvailability("","",Available,ref);
        connectedAvailable.addAvailability(rowTest,columnTest);
    }

    @Test
    public void addAvailabilityInParticularSlot(){
        connectedA.addAvailability(rowTest, columnTest);
        checkHeadTrueTailFalse(connectedA);
    }

    @Test
    public void removeAvailabilityInParticularSlot(){
        connectedAvailable.removeAvailability(rowTest, columnTest);
        checkAllFalse(connectedAvailable);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void negativeRow(){
        connectedA.addAvailability(-1, 0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void negativeColumn(){
        connectedA.addAvailability(0, -1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void rowOutOfBound(){
        connectedA.addAvailability(7,0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void columnOutOfBound(){ connectedA.addAvailability(0,11);
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

