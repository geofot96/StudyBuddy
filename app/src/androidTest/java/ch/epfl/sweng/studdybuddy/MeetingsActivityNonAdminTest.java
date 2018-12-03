package ch.epfl.sweng.studdybuddy;

import android.os.Bundle;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;

public class MeetingsActivityNonAdminTest {
    @Rule
    public IntentsTestRule<MeetingsActivity> mActivityRule = new IntentsTestRule<>(MeetingsActivity.class);

    @BeforeClass
    public static void setup(){
        MeetingsActivityTest.setup();
        Bundle bundle = new Bundle();
        bundle.putString(Messages.ADMIN, Messages.TEST);
        GlobalBundle.getInstance().putAll(bundle);
    }


    @Test
    public void oneCardViewInFeedback() throws Throwable {
        ViewInteraction vi = onView(withId(R.id.meetingRV));
        vi.perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(MapsActivity.class.getName()));
    }

}
