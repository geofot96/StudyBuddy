package ch.epfl.sweng.studdybuddy.InstrumentedTests;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.DummyMainActivity;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GroupsActivityLeadsToCreateGroup
{

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule(DummyMainActivity.class);


    @Test
    public void addGroupButtonExists()
    {
        refactor();
        ViewInteraction linearLayout = onView(withId(R.id.createGroup));
        linearLayout.check(matches(isDisplayed()));
    }

    @Test
    public void algoTest()
    {
        refactor();
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text), childAtPosition(
                                allOf(withId(R.id.search_plate), childAtPosition(
                                                withId(R.id.search_edit_frame),
                                                1)), 0), isDisplayed()));
        searchAutoComplete.perform(replaceText("Algorithms CS-250"),
                closeSoftKeyboard());
        ViewInteraction searchAutoComplete3 = onView(
                allOf(withId(R.id.search_src_text), withText("Algorithms CS-250"),
                        childAtPosition(
                                allOf(withId(R.id.search_plate), childAtPosition(
                                        withId(R.id.search_edit_frame), 1)), 0), isDisplayed()));
        searchAutoComplete3.perform(closeSoftKeyboard());
        try { Thread.sleep(5000);
        } catch(InterruptedException e) { e.printStackTrace(); }
        ViewInteraction textView = onView(
                allOf(withId(R.id.group_course_name), withText("Algorithms CS-250"), childAtPosition(
                                childAtPosition(withId(R.id.feedRecycleViewer), 0), 0),
                        isDisplayed()));
        textView.check(matches(withText("Algorithms CS-250")));
    }

    @Test
    public void searchBarExists()
    {
        refactor();
        ViewInteraction linearLayout = onView(withId(R.id.feed_search));
        linearLayout.check(matches(isDisplayed()));
    }
    @Test
    public void recyclerViewerExists()
    {
        refactor();
        ViewInteraction linearLayout = onView(withId(R.id.feedRecycleViewer));
        linearLayout.check(matches(isDisplayed()));
    }
    @Test
    public void sortButtonExists()
    {


        refactor();
        ViewInteraction linearLayout = onView(withId(R.id.sortButton));
        linearLayout.check(matches(isDisplayed()));
    }

    @Test
    public void thereIsAlgorithmsGroup()
    {
        /*refactor();
        ViewInteraction textView = onView(
                allOf(withId(R.id.group_course_name), withText("Algorithms CS-250"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.feedRecycleViewer),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Algorithms CS-250")));*/
    }
    void refactor(){
        try
        {
            Thread.sleep(7000);
        } catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.gotoGroups), withText("Groups"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        try
        {
            Thread.sleep(7000);
        } catch(InterruptedException e)
        {
            e.printStackTrace();
        }
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
}