package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

//Suggestions refers to courses database

@RunWith(AndroidJUnit4.class)
public class CourseSelectActivityTest {
    @Rule
    public final ActivityTestRule<CourseSelectActivity> mActivityRule =
            new ActivityTestRule<>(CourseSelectActivity.class);

    @Test
    public void skipLeadsToMainActivity() {
        onView(withId(R.id.skipButton)).perform(click());
        //intended(hasComponent(MainActivity.class.getName()));
    }


    @Test
    public void doneIsHiddenIfNoCourseSelected() {
        onView(withId(R.id.doneButton)).check(matches(not(isEnabled())));
        //select a course
        //delete a course
        //type a course
    }


    @Test //()
    public void courseNotAddedIfGibberish() {
        onView(withId(R.id.courseComplete)).perform(click(), typeText("yxcvbn"), pressImeActionButton());
        onView(withId(R.id.coursesSet)).check(matches(not(hasDescendant(withText("yxcvbn")))));
    }

    @Test //()
    public void enterOnValidInputAddsCourseToList() {
        //Change to soft coded value
        onView(withId(R.id.courseComplete)).perform(click(), typeText("Algorithms"), pressKey(KeyEvent.KEYCODE_ENTER), pressImeActionButton());
        onView(withId(R.id.coursesSet)).check(matches(hasDescendant(withText("Algorithms"))));
    }

    @Test
    public void clickOnCourseSuggestionAddsCourseToList() {
        onView(withId(R.id.courseComplete)).perform(click());
        //onView(desc)
        //onView(withId(R.id.courseField)).perform();
    }

    //swipe on course
}


