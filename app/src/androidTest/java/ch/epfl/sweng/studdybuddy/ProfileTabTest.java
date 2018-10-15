package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.MainActivity;
import ch.epfl.sweng.studdybuddy.activities.ProfileTab;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import ch.epfl.sweng.studdybuddy.activities.CourseSelectActivity;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.studdybuddy.CourseSelectActivityTest.mockCourse;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class ProfileTabTest {

    public static final String mockCourse = "Algorithms";

    @Rule
    public final ActivityTestRule<ProfileTab> mActivityRule =
            new ActivityTestRule<>(ProfileTab.class);

    @Test
    public void skipLeadsToMainActivity()
    {
        onView(withId(R.id.go_to_profile_btn)).perform(click());
        intended(toPackage(ProfileTab.class.getName()));
    }

/*
    public static final String mockCourse = "Algorithms";
    @Rule
    public final ActivityTestRule<CourseSelectActivity> mActivityRule =
            new ActivityTestRule<>(CourseSelectActivity.class);

    @Test
    public void skipLeadsToMainActivity()
    {
        //onView(withId(R.id.skipButton)).perform(click());
        //intended(toPackage(MainActivity.class.getName()));
    }


>>>>>>> Added an empty test

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
       // onView(withId(R.id.courseField)).perform(typeText(coursesDB[0]));
        //onView(withId(R.id.courseField)).perform(pressImeActionButton());
        //onView(withId(R.id.courseView)).check(matches());
        onView(withId(R.id.courseComplete)).perform(pressImeActionButton());


        //Change to soft coded value

    }

    @Test
    public void clickOnCourseSuggestionAddsCourseToList() {
        onView(withId(R.id.courseComplete)).perform(click());

    }

    //swipe on course


    public static ViewAction withCustomConstraints(final ViewAction action, final Matcher<View> constraints) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return constraints;
            }

            @Override
            public String getDescription() {
                return action.getDescription();
            }

            @Override
            public void perform(UiController uiController, View view) {
                action.perform(uiController, view);
            }
        };
    }
*/
}
