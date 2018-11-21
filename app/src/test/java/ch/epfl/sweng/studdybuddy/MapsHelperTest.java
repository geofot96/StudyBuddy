package ch.epfl.sweng.studdybuddy;

import android.widget.Button;

import com.google.android.gms.internal.maps.zzt;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapsHelperTest {

    @Test
    public void confirmationListenerTest(){
        MeetingLocation pos = new MeetingLocation("a","rolex", 1,2);
        Meeting m = new Meeting("a");
        m.setLocation(pos);
        List<Meeting> meetings = Arrays.asList(m);
        FirebaseReference ref = mock(FirebaseReference.class);
        when(ref.select(anyString())).thenReturn(ref);

        assertTrue(MapsHelper.confirmationListener(pos, ref, new ID<>("test"), new ID<>("test")));
        assertTrue(!MapsHelper.confirmationListener(null, ref, new ID<>("test"),new ID<>("test")));
    }

    //TODO had to remove the extensions MockMacker in resource files as it made tests crash on a branch
    // => needs to find a way to mock a final class

    /*
    @Test
    public void acceptSelectedPlaceTest(){
        Place place = mock(Place.class);
        String name = "a";
        String address = "b";
        LatLng pos = new LatLng(1,2);
        when(place.getAddress()).thenReturn(address);
        when(place.getName()).thenReturn(name);
        when(place.getLatLng()).thenReturn(pos);


        Marker marker = mock(Marker.class);

        MeetingLocation location =
                MapsHelper.acceptSelectedPlace(place, marker);


        assertTrue(location.equals(new MeetingLocation(name, address, pos)));
    }
    */

    @Test
    public void acceptMeetingsTest(){
        Meeting m = new Meeting("meet");
        m.setLocation(new MeetingLocation("Rolex", "Center",1,2));

        List<Meeting> meetingsFB = Arrays.asList(m);
        List<Meeting> meetings = new ArrayList<>();
        Button button = mock(Button.class);

        String uId = "Bouba";
        MarkerOptions mo = MapsHelper.acceptMeetings(meetingsFB, meetings, button,"Bouba");
        assertTrue(mo.getTitle().equals("Rolex") && mo.getPosition().equals(new LatLng(1,2)));

    }

}
