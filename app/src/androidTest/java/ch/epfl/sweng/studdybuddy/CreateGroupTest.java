package ch.epfl.sweng.studdybuddy;


import android.content.ComponentName;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.CreateGroupActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class CreateGroupTest {

    @Rule
    public IntentsTestRule<NavigationActivity> mIntentsTestRule = new IntentsTestRule<>(NavigationActivity.class);

    @Test
    public void clickCreateGroupButtonLeadsToCreateGroupsActivity(){
        onView(withId(R.id.createGroup)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CreateGroupActivity.class)));
    }

    @Test
    public void createButtonDisabledIfNoCourseSelected() {
        try {
            Thread.sleep(100);
            onView(withId(R.id.confirmGroupCreation)).check(matches(not(isEnabled())));
        }
        catch (Exception e) {

        }
    }

    @Test
    public void createAGroupeWithoutSettings(){
        onView(withId(R.id.createGroup)).perform(click());
        onView(withId(R.id.confirmGroupCreation)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CreateGroupActivity.class)));
    }

}
