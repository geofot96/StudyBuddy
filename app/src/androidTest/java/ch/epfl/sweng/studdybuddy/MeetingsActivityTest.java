package ch.epfl.sweng.studdybuddy;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.DateTimeHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.app.Activity.RESULT_OK;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

public class MeetingsActivityTest {
    MetaMeeting mM = mock(MetaMeeting.class);
    Date date = new Date();
    Calendar c = Calendar.getInstance();
    MeetingLocation meeL = new MeetingLocation(Messages.TEST, Messages.TEST, 0,0);
    List<Meeting> meeList;

    @Rule
    public IntentsTestRule<MeetingsActivity> mActivityRule = new IntentsTestRule<>(MeetingsActivity.class, false, false);

    @BeforeClass
    public static void setup(){
        GroupActivityTest.setup();
        Bundle bundle = new Bundle();
        bundle.putString(Messages.LOCATION_TITLE, Messages.TEST);
        bundle.putString(Messages.ADDRESS, Messages.TEST);
        GlobalBundle.getInstance().putAll(bundle);
    }

    @Before
    public void setUpMeeting(){
        Meeting mee = new Meeting(date.getTime(), date.getTime(), meeL, Messages.TEST);
        meeList = Arrays.asList(mee);
    }

    @Test
    public void oneCardViewInFeedback() throws Throwable {
        MeetingsActivity activity = mActivityRule.launchActivity(new Intent());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setMeetingList(meeList);
            }
        });
        int RealMonth = c.get(Calendar.MONTH)+1;
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        onView(withId(R.id.meetingRV)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.meetingDate))).check(
                matches(withText(
                        RealMonth+"/"+c.get(Calendar.DAY_OF_MONTH)+" From: "
                        + hour+":"+minute+" To: "+hour+":"+minute
                )));
        onView(allOf(withId(R.id.meetingLocation))).check(
                matches(withText("test: test"))
        );
        mActivityRule.finishActivity();
    }

   @Test
    public void testOnActivityResult() throws Throwable {
        MeetingsActivity activity = mActivityRule.launchActivity(new Intent());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setMetaM(mM);
                activity.onActivityResult(1, RESULT_OK, new Intent());
            }
        });
        verify(mM, times(1)).pushLocation(any(), any(), any());
        mActivityRule.finishActivity();
    }

    @Test
    public void leadsToCreateMeetingActivity() throws Throwable {
        MeetingsActivity activity = mActivityRule.launchActivity(new Intent());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setMeetingList(meeList);
            }
        });
        onView(allOf(withId(R.id.cardViewMeeting))).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), createMeetingActivity.class)));
        mActivityRule.finishActivity();
    }

    @Test
    public void leadsToMapsActivity() throws Throwable{
        Bundle b = new Bundle();
        b.putString(Messages.ADMIN, "...");
        GlobalBundle.getInstance().putAll(b);
        MeetingsActivity activity = mActivityRule.launchActivity(new Intent());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.setMeetingList(meeList);
            }
        });
        onView(allOf(withId(R.id.cardViewMeeting))).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), createMeetingActivity.class)));
        onView(withId(R.id.datePicker)).check(matches(withText(DateTimeHelper.printLongDate(date.getTime()))));
        onView(withId(R.id.timePicker)).check(matches(withText(DateTimeHelper.printTime(date.getTime()))));
        onView(withId(R.id.timePicker2)).check(matches(withText(DateTimeHelper.printTime(date.getTime()))));
        b.putString(Messages.ADMIN, Messages.TEST);
        GlobalBundle.getInstance().putAll(b);
        mActivityRule.finishActivity();
    }
}
