package ch.epfl.sweng.studdybuddy;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;

import static ch.epfl.sweng.studdybuddy.AvailabilityTest.checkAllFalse;
import static ch.epfl.sweng.studdybuddy.AvailabilityTest.checkHeadTrueTailFalse;
import static org.junit.Assert.assertEquals;
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
        connectedAvailable.modifyAvailability(rowTest,columnTest);
    }

    @Test
    public void addAvailabilityInParticularSlot(){
        connectedA.modifyAvailability(rowTest, columnTest);
        checkHeadTrueTailFalse(connectedA, rowTest, columnTest);
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

}

