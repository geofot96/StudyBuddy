package ch.epfl.sweng.studdybuddy;

import android.content.ComponentName;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.CreateGroupActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class GroupsActivityTest
{
    @Rule
    public final IntentsTestRule<GroupsActivity> mActivityRule =
            new IntentsTestRule<>(GroupsActivity.class);

    @Test
    public void plusButtonLeadsToCreateGroupActivity() throws InterruptedException
    {
        Thread.sleep(1000);
        onView(withId(R.id.createGroup)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CreateGroupActivity.class)));
    }

    @Test
    public void WeCanClickOnSortButton() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.sortButton)).perform(click());
    }
    @Test
    public void filterOutFullGroupsToggleButtonActivates()
    {
        onView(withId(R.id.toggleButton)).perform(click());
        onView(withId(R.id.toggleButton)).perform(click());
    }
}
