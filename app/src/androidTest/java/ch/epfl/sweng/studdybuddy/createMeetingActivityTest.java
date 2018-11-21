package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class createMeetingActivityTest {
    private MeetingLocation mockLocation = mock(MeetingLocation.class);
    private Date alwaysBefore = mock(Date.class);
    private Date alwaysAfter = mock(Date.class);


    @Rule
    public myRule mActivityRule =
            new myRule(createMeetingActivity.class);

    @Before
    public void setUp(){
        when(alwaysBefore.before(any())).thenReturn(true);
        when(alwaysAfter.after(any())).thenReturn(true);
    }

    @Test
    public void EveryThingIsDisplayed(){
        onView(withId(R.id.textView)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.textView3)).check(matches(isDisplayed()));
        onView(withId(R.id.textView4)).check(matches(isDisplayed()));
        onView(withId(R.id.datePicker)).check(matches(isClickable()));
        onView(withId(R.id.timePicker)).check(matches(isClickable()));
        onView(withId(R.id.timePicker2)).check(matches(isClickable()));
        onView(withId(R.id.locationTitle)).check(matches(isClickable()));
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
    }

    @Test
    public void ButtonIsEnabled(){
        createMeetingActivity mActivity = mActivityRule.getActivity();
        mActivity.setLocation(mockLocation);
        mActivity.setStartingDate(alwaysAfter);
        mActivity.setEndingDate(alwaysAfter);
        onView(withId(R.id.setMeeting)).check(matches(isEnabled()));
        onView(withId(R.id.setMeeting)).check(matches(isClickable()));
    }

    @Test
    public void WrongTimeSlot(){
        createMeetingActivity mActivity = mActivityRule.getActivity();
        mActivity.setLocation(mockLocation);
        mActivity.setStartingDate(alwaysAfter);
        mActivity.setEndingDate(alwaysBefore);
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
    }

    @Test
    public void noLocation(){
        createMeetingActivity mActivity = mActivityRule.getActivity();
        mActivity.setStartingDate(alwaysBefore);
        mActivity.setEndingDate(alwaysAfter);
        onView(withId(R.id.setMeeting)).check(matches(not(isEnabled())));
    }

    public static Intent setUpTestIntent(){
        Intent intent = new Intent();
        intent.putExtra(Messages.maxUser, 1);
        intent.putExtra(Messages.userID, Messages.TEST);
        intent.putExtra(Messages.groupID, Messages.TEST);
        return intent;
    }

    private class myRule extends ActivityTestRule<createMeetingActivity> {


        public myRule(Class<createMeetingActivity> activityClass) {
            super(activityClass);
        }

        @Override
        public createMeetingActivity launchActivity(@Nullable Intent startIntent) {
            Intent intent = setUpTestIntent();
            return super.launchActivity(intent);
        }
    }

}
