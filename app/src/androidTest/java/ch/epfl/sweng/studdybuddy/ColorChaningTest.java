package ch.epfl.sweng.studdybuddy;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.auth.DummyMainActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ColorChaningTest
{

    @Rule
    public ActivityTestRule<DummyMainActivity> mActivityTestRule = new ActivityTestRule<>(DummyMainActivity.class);

    @Test
    public void colorChaningTest()
    {
        /*// Added a sleep statement to match the app's execution delay.
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
        cardView.perform(closeSoftKeyboard(), click());

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
        cardView2.perform(closeSoftKeyboard(), click());

        ViewInteraction cardView3 = onView(
                childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        69));
        cardView3.perform(closeSoftKeyboard(), click());

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
        frameLayout4.check(matches(isDisplayed()));*/
    }
}
