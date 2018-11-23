package ch.epfl.sweng.studdybuddy;

import android.widget.Button;

import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapsHelperTest {


    //TODO had to remove the extensions MockMacker in resource files as it made tests crash on a branch
    // => needs to find a way to mock a final class


    @Test
    public void acceptSelectedPlaceTest(){
        Place place = mock(Place.class);
        String name = "a";
        String address = "b";
        LatLng pos = new LatLng(1,2);
        when(place.getAddress()).thenReturn(address);
        when(place.getName()).thenReturn(name);
        when(place.getLatLng()).thenReturn(pos);

        zzt mockZZT = mock(zzt.class);
        Marker marker = new Marker(mockZZT);

        MeetingLocation location =
                MapsHelper.acceptSelectedPlace(place, marker);


        assertTrue(location.equals(new MeetingLocation(name, address, pos)));
    }


    @Test
    public void acceptMeetingsTest(){
        Meeting m = new Meeting("meet");
        m.setLocation(new MeetingLocation("Rolex", "Center",1,2));

        Button button = mock(Button.class);

        String uId = "Bouba";
        MarkerOptions mo = MapsHelper.setInitialPosition(m);
        assertTrue(mo.getTitle().equals("Rolex") && mo.getPosition().equals(new LatLng(1,2)));

    }

}
