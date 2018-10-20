package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import ch.epfl.sweng.studdybuddy.activities.ProfileTab;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

//@RunWith(AndroidJUnit4.class)
public class ProfileTabTest
{
    /*public static final String mockCourse = "Algorithms";
    @Rule
    public final ActivityTestRule<ProfileTab> mActivityRule =
            new ActivityTestRule<>(ProfileTab.class);*/


    /*@Test
    public void signOutButtonLeadsToLogin() {
        onView(withId(R.id.signout)).perform(click());
        intended(toPackage(LoginActivity.class)).hasComponent();
    }*/

    /*@Test
    public void signOutButtonDisconnects() {

    }

    /*@Test
    public void profileListHasCorrectData() {
        onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("Linear Algebra"))));
        onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("Algorithms"))));
        onView(withId(R.id.courses_list)).check(matches(hasDescendant(withText("Computer Networks"))));
    }

    @Test
    public void groupListHasCorrectData() {
        onView(withId(R.id.groups_list)).check(matches(hasDescendant(withText("Linear Algebra"))));
        onView(withId(R.id.groups_list)).check(matches(hasDescendant(withText("Algorithms"))));
    }*/

}

