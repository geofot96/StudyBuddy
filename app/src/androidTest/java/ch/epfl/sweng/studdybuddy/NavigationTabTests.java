package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sweng.studdybuddy.GroupsActivityLeadsToCreateGroup.childAtPosition;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class NavigationTabTests
{

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityTestRule = new ActivityTestRule<>(NavigationActivity.class);

    @Test
    public void testClickOnProfile()
    {
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navToProfile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());
    }

    @Test
    public void testClickOnHome()
    {
        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.navToHome), withContentDescription("Home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView4.perform(click());
    }

    @Test
    public void testClickOnChat()
    {
        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.navToChat), withContentDescription("Chat"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView4.perform(click());
    }
}
