package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import ch.epfl.sweng.studdybuddy.activities.CourseSelectActivity;
import ch.epfl.sweng.studdybuddy.activities.MainActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

//Suggestions refers to courses database

@RunWith(AndroidJUnit4.class)
public class       CourseSelectActivityTest
{
    public static final String mockCourse = "Untitled";
    @Rule
    public final IntentsTestRule<CourseSelectActivity> mActivityRule =
            new IntentsTestRule<>(CourseSelectActivity.class);

    @Test
    public void skipLeadsToMainActivity()
    {
        onView(withId(R.id.skipButton)).perform(closeSoftKeyboard(), click());
        intended(hasComponent(GroupsActivity.class.getName()));
    }


    @Test
    public void doneIsHiddenIfNoCourseSelected()
    {
        onView(withId(R.id.doneButton)).check(matches(not(isEnabled())));
    }


    /*@Test
    public void clickOnCourseSuggestionAddsCourseToList() {
        //mActivityRule.getActivity().courseSelection.add(new Course(mockCourse));
        //mActivityRule.getActivity().adapter.notifyDataSetChanged();
         //onView(withId(R.id.courseComplete)).perform(click());
         //onData(equalTo(mockCourse)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
         //onView(withId(R.id.coursesSet)).check(matches(hasDescendant(withText(mockCourse))));
    }*/
}
