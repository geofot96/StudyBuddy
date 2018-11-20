package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.MapsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

public class MapsHelperTest {

    @Rule
    public IntentsTestRule<MapsActivity> mIntentsTestRule = new IntentsTestRule<>(MapsActivity.class);

    @Test
    public void acceptMeetingsTest(){

        onView(withId(R.id.confirmLocation)).check(matches(not(ViewMatchers.isDisplayed())));


    }
}
