package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.util.RequestCodes;

import static org.junit.Assert.assertTrue;

public class RequestCodesTest {
    @Test
    public void testEnum(){
        assertTrue(RequestCodes.MAPS.getRequestCode() == 1);
        assertTrue(RequestCodes.CREATEMEETING.getRequestCode() == 2);
    }

}
