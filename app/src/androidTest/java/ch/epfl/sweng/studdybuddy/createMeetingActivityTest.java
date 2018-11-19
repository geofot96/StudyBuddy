package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.studdybuddy.activities.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class createMeetingActivityTest {
    private Intent param;
    private MeetingLocation mockLocation;
    private Date alwaysBefore;
    private Date alwaysAfter;

    @Rule
    public ActivityTestRule<createMeetingActivity> mActivityRule =
            new ActivityTestRule<>(createMeetingActivity.class, false, false);

    @Before
    public void setUp(){
        param = new Intent();
        param.putExtra(Messages.groupID, Messages.TEST);
        param.putExtra(Messages.userID, Messages.TEST);
        param.putExtra(Messages.maxUser, Messages.TEST);
        mockLocation = mock(MeetingLocation.class);
        alwaysBefore = mock(Date.class);
        alwaysAfter = mock(Date.class);
        when(alwaysBefore.before(any())).thenReturn(true);
        when(alwaysAfter.after(any())).thenReturn(true);
    }

    @Test
    public void EveryThingIsDisplayed(){
        mActivityRule.launchActivity(param);
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
    public void ButtonIsEnabled(){
        createMeetingActivity mActivity = mActivityRule.launchActivity(param);
        mActivity.setLocation(mockLocation);
        mActivity.setStartingDate(alwaysAfter);
        mActivity.setEndingDate(alwaysAfter);
        onView(withId(R.id.setMeeting)).check(matches(isEnabled()));
        onView(withId(R.id.setMeeting)).check(matches(isClickable()));
        mActivityRule.finishActivity();
    }

    @Test
    public void WrongTimeSlot(){
        createMeetingActivity mActivity = mActivityRule.launchActivity(param);
        mActivity.setLocation(mockLocation);
        mActivity.setStartingDate(alwaysAfter);
        mActivity.setEndingDate(alwaysBefore);
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
        mActivityRule.finishActivity();
    }

    @Test
    public void noLocation(){
        createMeetingActivity mActivity = mActivityRule.launchActivity(param);
        mActivity.setStartingDate(alwaysBefore);
        mActivity.setEndingDate(alwaysAfter);
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
        mActivityRule.finishActivity();
    }


}
