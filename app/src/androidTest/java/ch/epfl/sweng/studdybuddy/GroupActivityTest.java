package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

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

public class GroupActivityTest {

    @Rule
    public IntentsTestRule<GroupActivity>  mAutomaticRule =
            new IntentsTestRule<>(GroupActivity.class);

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
        testIntent(R.id.calendarBtn, ConnectedCalendarActivity.class.getName());
    }

    @Test
    public void leadsToParticipants(){
        testIntent(R.id.participantsBtn, GroupInfoActivity.class.getName());
    }

    @Test
    public void NoAdminCantLeadToCreateMeeting(){
        mAutomaticRule.finishActivity();
        Bundle bundle = new Bundle();
        bundle.putString(Messages.ADMIN, "");
        GlobalBundle.getInstance().putAll(bundle);
        mManualRule.launchActivity(new Intent());
        onView(withId(R.id.createMeeting)).check(matches(not(isEnabled())));
        mManualRule.finishActivity();
        bundle.putString(Messages.ADMIN, Messages.TEST);
        GlobalBundle.getInstance().putAll(bundle);
    }

    @Test
    public void AdminCanLeadToCreateMeeting(){
        testIntent(R.id.createMeeting, createMeetingActivity.class.getName());
    }

    @Test
    public void leadsToMeetingsActivity(){
        testIntent(R.id.groupMeetingsBtn, MeetingsActivity.class.getName());
    }

    private void testIntent(int id, String name) {
        try {
            onView(withId(id)).perform(click());
            intended(hasComponent(name));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class myRule extends IntentsTestRule<GroupActivity> {


        public myRule(Class<GroupActivity> activityClass) {
            super(activityClass);
        }

        public myRule(Class<GroupActivity> activityClass, boolean b1, boolean b2) {
            super(activityClass, b1, b2);
        }

        @Override
        public GroupActivity launchActivity(@Nullable Intent startIntent) {
            Intent intent = new Intent();
            intent.putExtra(Messages.maxUser, 1);
            intent.putExtra(Messages.userID, Messages.TEST);
            intent.putExtra(Messages.groupID, Messages.TEST);
            String adminID = "";
            if(startIntent != null){
                adminID = Messages.TEST;
            }
            intent.putExtra(Messages.ADMIN, adminID);
            return super.launchActivity(intent);
        }
    }
}
