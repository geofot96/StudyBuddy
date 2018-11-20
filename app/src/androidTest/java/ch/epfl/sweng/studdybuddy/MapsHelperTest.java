package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.MapsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;
@RunWith(AndroidJUnit4.class)
public class MapsHelperTest {

    @Rule
    public ActivityTestRule<MapsActivity> mIntentsTestRule = new ActivityTestRule<>(MapsActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void setup(){}
    @Test
    public void acceptMeetingsTest(){
        try {
            Thread.sleep(2000);
            onView(withId(R.id.confirmLocation)).check(matches(not(ViewMatchers.isDisplayed())));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
