package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.MapsActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MapsActivityTest {

    @Rule
    public ActivityTestRule<MapsActivity> mIntentsTestRule = new ActivityTestRule<>(MapsActivity.class, true, false);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void setup() {
    }

    @Test
    public void checkConfirmDoesntExistForNonAdmin() {

       Intent intent = new Intent();
       intent.putExtra(Messages.groupID, "1");
       intent.putExtra(Messages.userID,"bouba");
       mIntentsTestRule.launchActivity(intent);
        try {
            Thread.sleep(2000);
            onView(withId(R.id.confirmLocation)).check(matches(not(ViewMatchers.isDisplayed())));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            Thread.sleep(2000);
//            onView(withId(R.id.confirmLocation)).check(matches(not(ViewMatchers.isDisplayed())));
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        mIntentsTestRule.finishActivity();
    }

    @Test
    public void checkConfirmExistForAdmin() {

        Intent intent = new Intent();
        intent.putExtra(Messages.groupID, "1");
        intent.putExtra(Messages.userID,"bouba");
        intent.putExtra(Messages.isAdmin,true);
        mIntentsTestRule.launchActivity(intent);
        try {
            Thread.sleep(2000);
            onView(withId(R.id.confirmLocation)).check(matches(ViewMatchers.isDisplayed()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mIntentsTestRule.finishActivity();
    }


    @Test
    public void mapListenerTest(){

       /* Intent intent = new Intent();
        intent.putExtra(Messages.groupID, "1");
        intent.putExtra(Messages.userID,"bouba");
        mIntentsTestRule.launchActivity(intent);
        zzt var1 = mock(zzt.class);
        Marker marker = new Marker(var1);
        Activity activity = mock(Activity.class);
      //  when(activity.getPackageName()).thenReturn("ch.epfl.sweng.studybuddy");
        PlaceAutocompleteFragment fragment = new PlaceAutocompleteFragment();
        MeetingLocation rolex = MapsHelper.ROLEX_LOCATION;
        MeetingLocation pos = MapsHelper.mapListener(rolex.getLatLng(), marker, fragment, mIntentsTestRule.getActivity());
        assertTrue(pos.equals(new MeetingLocation(rolex.getTitle(), rolex.getAddress(), rolex.getLatLng())));*/
    }
}

