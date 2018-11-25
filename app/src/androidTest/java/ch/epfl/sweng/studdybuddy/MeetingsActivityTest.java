package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.app.Activity.RESULT_OK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MeetingsActivityTest {
    MetaMeeting mM = mock(MetaMeeting.class);
    @Rule
    public ActivityTestRule<MeetingsActivity> mActivityRule = new ActivityTestRule<>(MeetingsActivity.class);

    @BeforeClass
    public static void setup(){
        GroupActivityTest.setup();
        Bundle bundle = new Bundle();
        bundle.putString(Messages.LOCATION_TITLE, Messages.TEST);
        bundle.putString(Messages.ADDRESS, Messages.TEST);
        GlobalBundle.getInstance().putAll(bundle);
    }

    @Test
    public void noCardViewInFeedback(){
        onView(withId(R.id.meetingRV)).check(matches(isDisplayed()));
    }

    @Test
    public void testOnActivityResult(){
        MeetingsActivity activity = mActivityRule.getActivity();
        activity.setMetaM(mM);
        activity.onActivityResult(1, RESULT_OK, new Intent());
        verify(mM, times(1)).pushLocation(any(), any(), any());
    }

}
