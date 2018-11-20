package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.MapsActivity;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.MeetingLocation;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    public void confirmationListenerTest(){
        MeetingLocation pos = new MeetingLocation("a","rolex", 1,2);
        Meeting m = new Meeting("a");
        m.setLocation(pos);
        List<Meeting> meetings = Arrays.asList(m);
        FirebaseReference ref = mock(FirebaseReference.class);
        when(ref.select(anyString())).thenReturn(ref);

        assertTrue(MapsHelper.confirmationListener(pos, meetings, ref));
        assertTrue(!MapsHelper.confirmationListener(null, meetings, ref));

    }
}
