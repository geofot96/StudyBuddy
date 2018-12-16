package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;

public class ConcreteAvailabilitiyTest {
    ConcreteAvailability concreteAvailability = new ConcreteAvailability();

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void negativeRow(){
        concreteAvailability.isAvailable(-1, 0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void negativeColumn(){
        concreteAvailability.isAvailable(0,-1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void rowBiggerThanMax(){
        concreteAvailability.isAvailable(11, 0);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void columnBiggerThanMax(){
        concreteAvailability.isAvailable(0,7);
    }
}
