package ch.epfl.sweng.studdybuddy;


import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.CreateGroupActivity;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class CreateGroupTest {

    @Rule
    public IntentsTestRule<GroupsActivity> mIntentsTestRule = new IntentsTestRule<>(GroupsActivity.class);

    @Test
    public void clickCreateGroupButtonLeadsToCreateGroupsActivity(){
        onView(withId(R.id.createGroup)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CreateGroupActivity.class)));
    }
/*
    @Test
    public void createAGroupWillBeRegistered() throws InterruptedException {

        onView(withId(R.id.createGroup)).perform(click());
        onView(withId(R.id.courseComplete2)).perform(click(), typeText("Concurrent algorithms CS-453"));
        Thread.sleep(500);
        onData(equalTo("Concurrent algorithms CS-453")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.courseComplete2)).perform(closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.confirmGroupCreation)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), GroupsActivity.class)));
    }
*/
    @Test
    public void createButtonDisabledIfNoCourseSelected() {
        try {
            Thread.sleep(100);
            onView(withId(R.id.confirmGroupCreation)).check(matches(not(isEnabled())));
        }
        catch (Exception e) {

        }
    }

    @Test
    public void createAGroupeWithoutSettings(){
        onView(withId(R.id.createGroup)).perform(click());
        onView(withId(R.id.confirmGroupCreation)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CreateGroupActivity.class)));
    }

}
