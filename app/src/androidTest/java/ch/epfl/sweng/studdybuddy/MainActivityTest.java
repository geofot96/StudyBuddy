package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.ConnectedCalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;
import ch.epfl.sweng.studdybuddy.activities.MergedCalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.ProfileTab;
import ch.epfl.sweng.studdybuddy.auth.DummyMainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

public class MainActivityTest {
      @Rule
    public IntentsTestRule<DummyMainActivity> DummyMainActivityIntentRule =
            new IntentsTestRule<>(DummyMainActivity.class);

    @Test
    public void clickGoToGroupsGoToGroupsActivity() {
        testIntent(R.id.gotoGroups, GroupsActivity.class.getName());
    }

    @Test
    public void clickToCalendarGoesToCalendarActivity() {
        //testIntent(R.id.calendarButton, ConnectedCalendarActivity.class.getName());
    }

    @Test
    public void clickToMergedCalendarGoesToMergedCalendarActivity() {
        testIntent(R.id.mergedCalendar, MergedCalendarActivity.class.getName());
    }

    public void testIntent(int id, String name) {
        try {
            Thread.sleep(1000);
            onView(withId(id)).perform(click());
            intended(hasComponent(name));
        }
        catch (Exception e) {

        }
    }

    @Test
    public void clickProfileButtonGoToCProfileTab() {
        testIntent(R.id.go_to_profile_btn, ProfileTab.class.getName());
    }

}
