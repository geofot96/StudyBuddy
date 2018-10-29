package ch.epfl.sweng.studdybuddy;


import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.GridLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.studdybuddy.GroupsActivityLeadsToCreateGroup.childAtPosition;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ColorChaningTest
{

    @Rule
    public ActivityTestRule<DummyMainActivity> mActivityTestRule = new ActivityTestRule<>(DummyMainActivity.class);

    @Test
    public void colorChaningTest()
    {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.calendarButton), withText("Calendar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html

        ViewInteraction cardView = onView(
                childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        1));
        cardView.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html

        ViewInteraction cardView2 = onView(
                childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        35));
        cardView2.perform(scrollTo(), click());

        ViewInteraction cardView3 = onView(
                childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        69));
        cardView3.perform(scrollTo(), click());

        ViewInteraction frameLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        1),
                        isDisplayed()));

        frameLayout.check(matches(isDisplayed()));

        ViewInteraction frameLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        35),
                        isDisplayed()));
        frameLayout2.check(matches(isDisplayed()));

        ViewInteraction frameLayout3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        69),
                        isDisplayed()));
        frameLayout3.check(matches(isDisplayed()));

        ViewInteraction frameLayout4 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        69),
                        isDisplayed()));
        frameLayout4.check(matches(isDisplayed()));
    }
}
