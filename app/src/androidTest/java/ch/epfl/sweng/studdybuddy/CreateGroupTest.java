/*
package ch.epfl.sweng.studdybuddy;


import android.content.Intent;
import android.os.SystemClock;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.NumberPicker;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateGroupTest
{

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Test
    public void CreateDefaultGroup()
    {
        mActivityTestRule.launchActivity(new Intent());
        ViewInteraction appCompatButton = onView(withId(R.id.gotoGroups));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(withId(R.id.createGroup));
        floatingActionButton.perform(click());

        ViewInteraction appCompatButton2 = onView(withId(R.id.confirmGroupCreation));
        appCompatButton2.perform(click());
        checkRecyclerview("", "Particip: 1/2", "Computer Science", "en");
    }


    @Test
    public void ChoosingACourse()
    {
        mActivityTestRule.launchActivity(new Intent());
        ViewInteraction appCompatButton = onView(withId(R.id.gotoGroups));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(withId(R.id.createGroup));
        floatingActionButton.perform(click());

        onView(withId(R.id.courseComplete2)).perform(click(), typeText("Ana"));
        onData(equalTo("Analysis 1")).inRoot(RootMatchers.isPlatformPopup()).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.confirmGroupCreation)).perform(click());

        checkRecyclerview("Analysis 1", "Particip 1/2", "Computer Science", "en");
    }

    @Test
    public void WrongInputLeadsToAGroupWithoutCourse()
    {
        mActivityTestRule.launchActivity(new Intent());
        ViewInteraction appCompatButton = onView(withId(R.id.gotoGroups));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(withId(R.id.createGroup));
        floatingActionButton.perform(click());

        onView(withId(R.id.courseComplete2)).perform(click(), typeText("Ana"));
        onData(equalTo("Analysis 1")).inRoot(RootMatchers.isPlatformPopup()).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.confirmGroupCreation)).perform(click());

        checkRecyclerview("", "Particip 1/2", "Computer Science", "en");
    }


    @Test
    public void CanInteractWithNumberPicker()
    {
        mActivityTestRule.launchActivity(new Intent());
        ViewInteraction appCompatButton = onView(withId(R.id.gotoGroups));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(withId(R.id.createGroup));
        floatingActionButton.perform(click());

        onView(withId(R.id.numberPicker)).perform(setNumber(7));

    }

    @Test
    public void CanChooseSection()
    {
        mActivityTestRule.launchActivity(new Intent());
        ViewInteraction appCompatButton = onView(withId(R.id.gotoGroups));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(withId(R.id.createGroup));
        floatingActionButton.perform(click());

        onView(withId(R.id.spinnerSection)).perform(click());
        onData(equalTo("Mathematics")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        onView(withId(R.id.spinnerSection)).check(matches(withSpinnerText(containsString("Mathematics"))));

    }

    @Test
    public void CanChooseLanguage()
    {
        mActivityTestRule.launchActivity(new Intent());
        ViewInteraction appCompatButton = onView(withId(R.id.gotoGroups));
        appCompatButton.perform(click());

        ViewInteraction floatingActionButton = onView(withId(R.id.createGroup));
        floatingActionButton.perform(click());

        onView(withId(R.id.spinnerLanguage)).perform(click());

        onData(equalTo("fr")).inRoot(RootMatchers.isPlatformPopup()).perform(click());

        onView(withId(R.id.spinnerLanguage)).check(matches(withSpinnerText(containsString("fr"))));

    }

    private void checkRecyclerview(String txt1, String txt2, String txt3, String txt4)
    {

        ViewInteraction textView = onView(
                allOf(withId(R.id.group_course_name),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.testRecycleViewer),
                                        2),
                                0),
                        isDisplayed()));
        textView.check(matches(withText(txt1)));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.group_participant_info),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.testRecycleViewer),
                                        2),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText(txt2)));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.group_section),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.testRecycleViewer),
                                        2),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText(txt3)));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.group_language),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.testRecycleViewer),
                                        2),
                                3),
                        isDisplayed()));
        textView4.check(matches(withText(txt4)));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position)
    {

        return new TypeSafeMatcher<View>()
        {
            @Override
            public void describeTo(Description description)
            {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view)
            {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ViewAction setNumber(final int n)
    {
        return new ViewAction()
        {
            @Override
            public void perform(UiController uiController, View view)
            {
                ((NumberPicker) view).setValue(n);
            }

            @Override
            public String getDescription()
            {
                return "Set NumberPicker value";
            }

            @Override
            public Matcher<View> getConstraints()
            {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }
        };
    }
}*/
