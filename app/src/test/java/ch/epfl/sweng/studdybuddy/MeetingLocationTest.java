package ch.epfl.sweng.studdybuddy;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.MeetingLocation;

import static junit.framework.TestCase.assertTrue;

public class MeetingLocationTest {

    @Test
    public void testEquals(){
        double latitude = 10;
        double longitude = 15;
        String nom = "EPFL";
        String address ="Rolex";
        MeetingLocation m1 = new  MeetingLocation(nom, address, new LatLng(latitude, longitude));
        MeetingLocation m2 = new MeetingLocation(nom, address, latitude, longitude);
        assertTrue(m1.equals(m2));
    }
}
