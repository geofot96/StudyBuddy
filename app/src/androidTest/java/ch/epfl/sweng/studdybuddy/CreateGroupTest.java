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

    @Test
    public void createAGroupWillBeRegistered() {

       /* onView(withId(R.id.createGroup)).perform(click());
        onView(withId(R.id.courseComplete2)).perform(click(), typeText("Concurrent algorithms CS-453"));
        onData(equalTo("Concurrent algorithms CS-453")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        onView(withId(R.id.courseComplete2)).perform(closeSoftKeyboard());
        onView(withId(R.id.confirmGroupCreation)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), GroupsActivity.class)));
*/
       /*// Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction appCompatAutoCompleteTextView = onView(
                allOf(withId(R.id.courseComplete2), withContentDescription("Courses")
                        ));
        appCompatAutoCompleteTextView.perform(click());

        ViewInteraction appCompatAutoCompleteTextView2 = onView(
                allOf(withId(R.id.courseComplete2), withContentDescription("Courses")));
        appCompatAutoCompleteTextView2.perform(replaceText("Algor"), closeSoftKeyboard());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatTextView.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinnerLanguage),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction customEditText = onView(
                allOf(withClassName(is("android.widget.NumberPicker$CustomEditText")), withText("5"),
                        childAtPosition(
                                allOf(withId(R.id.numberPicker),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        customEditText.perform(replaceText("7"));

        ViewInteraction customEditText2 = onView(
                allOf(withClassName(is("android.widget.NumberPicker$CustomEditText")), withText("7"),
                        childAtPosition(
                                allOf(withId(R.id.numberPicker),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        customEditText2.perform(closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.confirmGroupCreation), withText(" Create Group"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3474941);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.sortButton), withText("Sort"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.group_course_name), withText("Concurrent algorithms CS-453"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.feedRecycleViewer),
                                        1),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Concurrent algorithms CS-453")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.group_language), withText("Fr"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.feedRecycleViewer),
                                        1),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("Fr")));
*/
    }

    @Test
    public void createButtonDisabledIfNoCourseSelected() {
        onView(withId(R.id.confirmGroupCreation)).check(matches(not(isEnabled())));
    }

    @Test
    public void createAGroupeWithoutSettings(){
        onView(withId(R.id.createGroup)).perform(click());
        onView(withId(R.id.confirmGroupCreation)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), CreateGroupActivity.class)));
    }
}
