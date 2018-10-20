package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.CreateGroupActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class GroupsActivityTest
{
//    @Rule
//    public final ActivityTestRule<GroupsActivity> mActivityRule =
//            new ActivityTestRule<>(GroupsActivity.class);

    @Rule
    public IntentsTestRule<GroupsActivity> mActivityRule = new IntentsTestRule<GroupsActivity>(GroupsActivity.class);

    @Before
    public void init()
    {
        mActivityRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void plusButtonLeadsToCreateGroupActivity()
    {
        onView(withId(R.id.createGroup)).perform(click());
        intended(hasComponent(CreateGroupActivity.class.getName()));
    }
}
