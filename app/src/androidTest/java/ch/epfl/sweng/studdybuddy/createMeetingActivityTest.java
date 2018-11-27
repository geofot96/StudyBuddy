package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;

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
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static ch.epfl.sweng.studdybuddy.ExampleInstrumentedTest.matchesDate;
import static ch.epfl.sweng.studdybuddy.ExampleInstrumentedTest.matchesTime;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class createMeetingActivityTest {
    Intent intent = new Intent();
    private MeetingLocation mockLocation = mock(MeetingLocation.class);
    private Date alwaysBefore = mock(Date.class);
    private Date alwaysAfter = mock(Date.class);


    @Rule
    public ActivityTestRule<createMeetingActivity> mActivityRule =
            new ActivityTestRule<>(createMeetingActivity.class, false, false);

    @Rule
    public IntentsTestRule<createMeetingActivity> mIntentRule =
            new IntentsTestRule<>(createMeetingActivity.class, false, false);

   /* @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);
*/
    @BeforeClass
    public static void setUpBeforeClass(){
        MeetingsActivityTest.setup();
    }

    @Before
    public void setUpBefore(){
        when(alwaysBefore.before(any())).thenReturn(true);
        when(alwaysAfter.after(any())).thenReturn(true);
        MapsActivityTest.allowPermissionsIfNeeded(android.Manifest.permission.ACCESS_FINE_LOCATION);
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
    public void ButtonIsEnabled() throws Throwable {
        createMeetingActivity mActivity = mActivityRule.launchActivity(intent);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.setLocation(mockLocation);
                mActivity.setStartingDate(alwaysAfter);
                mActivity.setEndingDate(alwaysAfter);
            }
        });
        onView(withId(R.id.setMeeting)).check(matches(isEnabled()));
        onView(withId(R.id.setMeeting)).check(matches(isClickable()));
        mActivityRule.finishActivity();
    }


    @Test
    public void WrongTimeSlot() throws Throwable {
        createMeetingActivity mActivity = mActivityRule.launchActivity(intent);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.setLocation(mockLocation);
                mActivity.setStartingDate(alwaysAfter);
                mActivity.setEndingDate(alwaysBefore);
            }
        });
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
    public void testOnActivityResult() throws Throwable {
        createMeetingActivity mActivity = mActivityRule.launchActivity(intent);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.onActivityResult(1, Activity.RESULT_OK, new Intent());
            }
        });
        onView(withId(R.id.locationTitle)).check(matches(withText("test: test")));
        mActivityRule.finishActivity();
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

}
