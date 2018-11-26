package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.ConnectedCalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.GroupActivity;
import ch.epfl.sweng.studdybuddy.activities.group.GroupInfoActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

public class GroupActivityTest {

    @Rule
    public IntentsTestRule<GroupActivity>  mManualRule =
            new IntentsTestRule<>(GroupActivity.class, false, false);

    @BeforeClass
    public static void setup(){
        Bundle bundle = new Bundle();
        bundle.putInt(Messages.maxUser, 1);
        bundle.putString(Messages.userID, Messages.TEST);
        bundle.putString(Messages.groupID, Messages.TEST);
        bundle.putString(Messages.ADMIN, Messages.TEST);
        GlobalBundle.getInstance().putAll(bundle);
    }

    @Test
    public void leadsToCalendar(){
        mManualRule.launchActivity(new Intent());
        testIntent(R.id.calendarBtn, ConnectedCalendarActivity.class.getName());
        mManualRule.finishActivity();
    }

    @Test
    public void leadsToParticipants(){
        mManualRule.launchActivity(new Intent());
        testIntent(R.id.participantsBtn, GroupInfoActivity.class.getName());
        mManualRule.finishActivity();
    }

  /*  @Test
    public void NoAdminCantLeadToCreateMeeting(){
        Bundle bundle = new Bundle();
        bundle.putString(Messages.ADMIN, "");
        GlobalBundle.getInstance().putAll(bundle);
        bundle = GlobalBundle.getInstance().getSavedBundle();
        assertEquals("", bundle.getString(Messages.ADMIN));
        mManualRule.launchActivity(new Intent());
        onView(withId(R.id.createMeeting)).check(matches(not(isEnabled())));
        bundle.putString(Messages.ADMIN, Messages.TEST);
        GlobalBundle.getInstance().putAll(bundle);
        mManualRule.finishActivity();
    }*/

    @Test
    public void AdminCanLeadToCreateMeeting(){
        mManualRule.launchActivity(new Intent());
        testIntent(R.id.createMeeting, createMeetingActivity.class.getName());
        mManualRule.finishActivity();
    }

    @Test
    public void leadsToMeetingsActivity(){
        mManualRule.launchActivity(new Intent());
        testIntent(R.id.groupMeetingsBtn, MeetingsActivity.class.getName());
        mManualRule.finishActivity();
    }

  /*  @Test
    public void leadsToNavigationActivity(){
        Bundle bundle = new Bundle();
        bundle.putInt(Messages.maxUser, -1);
        GlobalBundle.getInstance().putAll(bundle);
        mManualRule.launchActivity(new Intent());
        intended(hasComponent(NavigationActivity.class.getName()));
        bundle.putInt(Messages.maxUser, 1);
        GlobalBundle.getInstance().putAll(bundle);
        mManualRule.finishActivity();
    }*/

    private void testIntent(int id, String name) {
        try {
            onView(withId(id)).perform(click());
            intended(hasComponent(name));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



}
