package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

public class createMeetingActivityTest {
  @Test
  public void EmptyTest(){}
/*    Intent intent = new Intent();
    private MeetingLocation mockLocation = mock(MeetingLocation.class);
    private Date alwaysBefore = mock(Date.class);
    private Date alwaysAfter = mock(Date.class);


    @Rule
    public ActivityTestRule<createMeetingActivity> mActivityRule =
            new ActivityTestRule<>(createMeetingActivity.class, false, false);

    @Rule
    public IntentsTestRule<createMeetingActivity> mIntentRule =
            new IntentsTestRule<>(createMeetingActivity.class, false, false);

    @BeforeClass
    public static void setUpBeforeClass(){
        MeetingsActivityTest.setup();
    }

    @Before
    public void setUpBefore(){
        when(alwaysBefore.before(any())).thenReturn(true);
        when(alwaysAfter.after(any())).thenReturn(true);
    }

    @Test
    public void EveryThingIsDisplayed(){
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
        onView(withId(R.id.textView4)).check(matches(isDisplayed()));
        onView(withId(R.id.datePicker)).check(matches(isClickable()));
        onView(withId(R.id.timePicker)).check(matches(isClickable()));
        onView(withId(R.id.timePicker2)).check(matches(isClickable()));
        onView(withId(R.id.locationTitle)).check(matches(isClickable()));
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
        mActivityRule.finishActivity();
    }

   @Test
    public void ButtonIsEnabled() {
        createMeetingActivity mActivity = mActivityRule.launchActivity(intent);


                mActivity.setLocation(mockLocation);
                mActivity.setStartingDate(alwaysAfter);
                mActivity.setEndingDate(alwaysAfter);

        onView(withId(R.id.setMeeting)).check(matches(isEnabled()));
        onView(withId(R.id.setMeeting)).check(matches(isClickable()));
    }

    @Test
    public void WrongTimeSlot() {
        createMeetingActivity mActivity = mActivityRule.launchActivity(intent);

                mActivity.setLocation(mockLocation);
                mActivity.setStartingDate(alwaysAfter);
                mActivity.setEndingDate(alwaysBefore);

        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
        mActivityRule.finishActivity();
    }

    @Test
    public void noLocation(){
        createMeetingActivity mActivity = mActivityRule.launchActivity(intent);
        mActivity.setStartingDate(alwaysBefore);
        mActivity.setEndingDate(alwaysAfter);
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
        mActivityRule.finishActivity();
    }

    @Test
    public void leadsToMapsActivity(){
        mIntentRule.launchActivity(intent);
        onView(withId(R.id.locationTitle)).perform(click());
        intended(hasComponent(MapsActivity.class.getName()));
        mIntentRule.finishActivity();
    }

    @Test
    public void testOnActivityResult(){
        createMeetingActivity mActivity = mActivityRule.launchActivity(intent);
        mActivity.onActivityResult(1, Activity.RESULT_OK, new Intent());
        onView(withId(R.id.locationTitle)).check(matches(withText("test: test")));
    }


    private void timerDialog(int id){
        mActivityRule.launchActivity(intent);
        onView(withId(id)).perform(click());
        ViewInteraction tP = onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(0,0));
        tP.check(matches(matchesTime(0,0)));
        mActivityRule.finishActivity();
    }

    @Test
    public void timerDialog1(){
        timerDialog(R.id.timePicker);
    }

    @Test
    public void timerDialog2(){
        timerDialog(R.id.timePicker2);
    }

    @Test
    public void dateDialog(){
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.datePicker)).perform(click());
        ViewInteraction dP = onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2018,10+1, 25));
        dP.check(matches(matchesDate(2018,10, 25)));
        mActivityRule.finishActivity();
    }

    private static Matcher<View> matchesTime(final int hours, final int minutes) {
        return new BoundedMatcher<View, TimePicker>(TimePicker.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("matches date:");
            }

            @Override
            protected boolean matchesSafely(TimePicker item) {
                int h, min;
                int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentApiVersion > android.os.Build.VERSION_CODES.LOLLIPOP_MR1){
                    h = item.getHour();
                    min = item.getMinute();
                } else {
                    h = item.getCurrentHour();
                    min = item.getCurrentMinute();
                }
                return (hours == h && minutes == min);
            }
        };
    }

    public static Matcher<View> matchesDate(final int year, final int month, final int day) {
        return new BoundedMatcher<View, DatePicker>(DatePicker.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("matches date:");
            }

            @Override
            protected boolean matchesSafely(DatePicker item) {
                return (year == item.getYear() && month == item.getMonth() && day == item.getDayOfMonth());
            }
        };
    }
    */

}
