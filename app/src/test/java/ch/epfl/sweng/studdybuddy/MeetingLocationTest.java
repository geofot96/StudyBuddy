package ch.epfl.sweng.studdybuddy;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.MeetingLocation;

import static junit.framework.TestCase.assertTrue;

public class MeetingLocationTest {
    MeetingLocation m1;
    MeetingLocation m2;
    MeetingLocation m3;
    @Before
    public void setup() {
        double latitude = 10;
        double longitude = 15;
        String nom = "EPFL";
        String address ="Rolex";
        m1 = new MeetingLocation(nom, address, new LatLng(latitude, longitude));
        m2 = new MeetingLocation(nom, address, latitude, longitude);
        m3 = new MeetingLocation("IN", "Place Turing", new LatLng(latitude + 1, longitude+1));

    }
    @Test
    public void testEquals(){
        assertTrue(m1.equals(m2));
        assertTrue(m1.equals(m1));
        assertTrue(!m1.equals(m3));
    }

    @Test
    public void setWorks(){
        m1.setAddress(m3.getAddress());
        m1.setLatitude(m3.getLatitude());
        m1.setLongitude(m3.getLongitude());
        m1.setTitle(m3.getTitle());
    }

    @Test
    public void getLatLng(){
        LatLng pos = new LatLng(1,2);
        MeetingLocation m3 = new MeetingLocation("a","b", pos);
        assertTrue(m3.getLatitude() == pos.latitude && m3.getLongitude() == pos.longitude);
    }

}
