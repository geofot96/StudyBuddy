package ch.epfl.sweng.studdybuddy;

import android.content.ComponentName;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.CourseSelectActivity;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

//Suggestions refers to courses database

@RunWith(AndroidJUnit4.class)
public class CourseSelectActivityTest
{
  public static final String mockCourse = "Concurrent algorithms CS-453";

    @Rule
    public final IntentsTestRule<CourseSelectActivity> mActivityRule =
            new IntentsTestRule<>(CourseSelectActivity.class);

    @Test
    public void skipLeadsToMainActivity() {
        onView(withId(R.id.skipButton)).perform(closeSoftKeyboard(), click());
        intended(hasComponent(GroupsActivity.class.getName()));

    }


    @Test
    public void skipLeadsToGroupActivity() throws InterruptedException {
        Thread.sleep(500);
        onView(withId(R.id.skipButton)).perform(closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.skipButton)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), GroupsActivity.class)));
    }



    @Test
    public void doneIsHiddenIfNoCourseSelected()
    {
        onView(withId(R.id.doneButton)).check(matches(not(isEnabled())));
        //select a course
        //delete a course
        //type a course
    }


    @Test //()
    public void courseNotAddedIfGibberish() throws InterruptedException {
        onView(withId(R.id.courseComplete)).perform(click(), typeText("yxcvbn"));
        onView(withId(R.id.doneButton)).perform(closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.doneButton)).check(matches(not(isEnabled())));
    }

    /*@Test
    public void clickOnCourse() {
        try {
            onView(withId(R.id.courseComplete)).perform(click());
            Thread.sleep(200);
            onData(withText(mockCourse)).perform(click());
            onView(withId(R.id.doneButton)).check(matches(isEnabled()));
            //onView(withId(R.id.coursesSet)).check();
        }
        catch (Exception e) {
        }
    }*/
}
