package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.CalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.ProfileTab;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.studdybuddy.GroupsActivityLeadsToCreateGroup.childAtPosition;
import static org.hamcrest.Matchers.allOf;

public class CalendarActivityTest
{
    @Rule
    public final IntentsTestRule<CalendarActivity> mActivityRule =
            new IntentsTestRule<>(CalendarActivity.class);

    @Test
    public void confirmLeadsToProfile()
    {
        onView(withId(R.id.confirmSlots)).perform(closeSoftKeyboard(), click());
        intended(hasComponent(ProfileTab.class.getName()));
    }

    @Test
    public void testConfirmButtonExists()
    {
        ViewInteraction button = onView(
                allOf(withId(R.id.confirmSlots),
                        childAtPosition(
                                allOf(withId(R.id.generalThing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                2),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void testDaysLayoutExists()
    {
        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.days),
                        childAtPosition(
                                allOf(withId(R.id.generalThing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                0),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));
    }

    @Test
    public void testCalendarGridtExists()
    {
        ViewInteraction gridLayout = onView(
                allOf(withId(R.id.calendarGrid),
                        childAtPosition(
                                allOf(withId(R.id.generalThing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                1),
                        isDisplayed()));
        gridLayout.check(matches(isDisplayed()));
    }

    @Test
    public void testMondayExists()
    {
        ViewInteraction textView = onView(
                allOf(withId(R.id.Monday), withText("Mon"),
                        childAtPosition(
                                allOf(withId(R.id.days),
                                        childAtPosition(
                                                withId(R.id.generalThing),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Mon")));
    }

    @Test
    public void testTuesdayExists()
    {
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.Tuesday), withText("Tue"),
                        childAtPosition(
                                allOf(withId(R.id.days),
                                        childAtPosition(
                                                withId(R.id.generalThing),
                                                0)),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Tue")));
    }

    @Test
    public void testWednesdayExists()
    {
        ViewInteraction textView3 = onView(
                allOf(withId(R.id.Wednesday), withText("Wed"),
                        childAtPosition(
                                allOf(withId(R.id.days),
                                        childAtPosition(
                                                withId(R.id.generalThing),
                                                0)),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("Wed")));
    }

    @Test
    public void testThursdayExists()
    {
        ViewInteraction textView4 = onView(
                allOf(withId(R.id.Thursday), withText("Thu"),
                        childAtPosition(
                                allOf(withId(R.id.days),
                                        childAtPosition(
                                                withId(R.id.generalThing),
                                                0)),
                                3),
                        isDisplayed()));
        textView4.check(matches(withText("Thu")));
    }

    @Test
    public void testFridayExists()
    {
        ViewInteraction textView5 = onView(
                allOf(withId(R.id.Friday), withText("Fri"),
                        childAtPosition(
                                allOf(withId(R.id.days),
                                        childAtPosition(
                                                withId(R.id.generalThing),
                                                0)),
                                4),
                        isDisplayed()));
        textView5.check(matches(withText("Fri")));
    }

    @Test
    public void testSaturdayExists()
    {
        ViewInteraction textView6 = onView(
                allOf(withId(R.id.Saturday), withText("Sat"),
                        childAtPosition(
                                allOf(withId(R.id.days),
                                        childAtPosition(
                                                withId(R.id.generalThing),
                                                0)),
                                5),
                        isDisplayed()));
        textView6.check(matches(withText("Sat")));
    }

    @Test
    public void testSundayExists()
    {
        ViewInteraction textView7 = onView(
                allOf(withId(R.id.Sunday), withText("Sun"),
                        childAtPosition(
                                allOf(withId(R.id.days),
                                        childAtPosition(
                                                withId(R.id.generalThing),
                                                0)),
                                6),
                        isDisplayed()));
        textView7.check(matches(withText("Sun")));
    }



//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position)
//    {
//
//        return new TypeSafeMatcher<View>()
//        {
//            @Override
//            public void describeTo(Description description)
//            {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view)
//            {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }

}
