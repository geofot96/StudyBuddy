package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
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
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

//Suggestions refers to courses database

@RunWith(AndroidJUnit4.class)
public class CourseSelectActivityTest {
    @Rule
    public final ActivityTestRule<CourseSelectActivity> mActivityRule =
            new ActivityTestRule<>(CourseSelectActivity.class);

    @Test
    public void skipLeadsToMainActivity() {
        //onView(withId(R.id.skipButton)).perform(click()).check();
    }


    @Test
    public void doneIsHiddenIfNoCourseSelected() {

    }


    @Test //()
    public void courseNotAddedIfGibberish() {
        onView(withId(R.id.courseComplete)).perform(click(), typeText("yxcvbn"), pressImeActionButton());
        //onView(withId(R.id.coursesSet)).check()

    }

    @Test //()
    public void enterOnValidInputAddsCourseToList() {
        onView(withId(R.id.courseComplete)).perform(pressImeActionButton());
       // onView(withId(R.id.courseField)).perform(typeText(coursesDB[0]));
        //onView(withId(R.id.courseField)).perform(pressImeActionButton());
        //onView(withId(R.id.courseView)).check(matches());
    }

    @Test
    public void clickOnCourseSuggestionAddsCourseToList() {
        onView(withId(R.id.courseComplete)).perform(click());
        //onView(withId(R.id.courseField)).perform();
    }

    //swipe on course
}


