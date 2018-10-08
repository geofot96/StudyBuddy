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

<<<<<<< HEAD
    @Test//()
    public void clickOnCourseFieldOpensSuggestions() {
        //onView(withId(R.id.courseField)).perform(click());
        onView(withId(R.id.courseComplete));
    }

    @Test //()
    public void suggestionsDntGrowWhenLetterTyped() {
=======
    @Test
    public void doneIsHiddenIfNoCourseSelected() {
>>>>>>> Fixed card deletion

    }

    @Test//()
    public void clickOnCourseFieldOpensSuggestions() {
        onView(withId(R.id.courseComplete)).perform(click());
        //onView(withId(R.id.courseComplete)).check(matches(isDisplayed()));
        AutoCompleteTextView tv;
    }

    @Test //()
    public void courseNotAddedIfGibberish() {
        onView(withId(R.id.courseComplete)).perform(click(), typeText("yxcvbn"), pressImeActionButton());
        //onView(withId(R.id.coursesSet)).check()

    }

    @Test //()
    public void enterOnValidInputAddsCourseToList() {
<<<<<<< HEAD
       // onView(withId(R.id.courseField)).perform(typeText(coursesDB[0]));
        //onView(withId(R.id.courseField)).perform(pressImeActionButton());
        //onView(withId(R.id.courseView)).check(matches());
=======
        onView(withId(R.id.courseComplete)).perform(pressImeActionButton());
>>>>>>> Fixed card deletion
    }

    @Test
    public void clickOnCourseSuggestionAddsCourseToList() {
<<<<<<< HEAD
        //onView(withId(R.id.courseField)).perform();
=======
        onView(withId(R.id.courseComplete)).perform(click());
>>>>>>> Fixed card deletion
    }

    //swipe on course
}


