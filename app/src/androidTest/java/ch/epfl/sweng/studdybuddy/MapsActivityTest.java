package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class MapsActivityTest {

    @Rule
    public ActivityTestRule<MapsActivity> mIntentsTestRule = new ActivityTestRule<>(MapsActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void setup(){}


    @Test
    public void checkConfirmDoesntExistForNonAdmin(){
        try {
            Thread.sleep(2000);
            onView(withId(R.id.confirmLocation)).check(matches(not(ViewMatchers.isDisplayed())));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Ignore
    @Test
    public void mapListenerTest(){
        Marker marker = mock(Marker.class);
        PlaceAutocompleteFragment fragment = new PlaceAutocompleteFragment();
        MeetingLocation rolex = MapsHelper.ROLEX_LOCATION;
        MeetingLocation pos = MapsHelper.mapListener(new LatLng(rolex.getLatitude(), rolex.getLongitude()), marker, fragment, mIntentsTestRule.getActivity());
        assertTrue(pos.equals(new MeetingLocation(rolex.getTitle(), rolex.getAddress(), new LatLng(rolex.getLatitude(), rolex.getLongitude()))));
    }
}
