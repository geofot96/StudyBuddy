package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.ConnectedCalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupInfoActivity;
import ch.epfl.sweng.studdybuddy.activities.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.activities.meetings.createMeetingActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
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
    public myRule mAutomaticRule =
            new myRule(GroupActivity.class);

    @Rule
    public myRule mManualRule =
            new myRule(GroupActivity.class, false, false);

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
        onView(withId(R.id.createMeeting)).check(matches(not(isEnabled())));
    }

    @Test
    public void AdminCanLeadToCreateMeeting(){
        mAutomaticRule.finishActivity();
        mManualRule.launchActivity(new Intent());
        testIntent(R.id.createMeeting, createMeetingActivity.class.getName());
        mManualRule.finishActivity();
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
