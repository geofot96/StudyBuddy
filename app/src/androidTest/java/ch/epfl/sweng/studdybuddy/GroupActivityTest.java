package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.GroupActivity;
import ch.epfl.sweng.studdybuddy.activities.group.InviteFriendsActivity;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.CreateMeetingActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class GroupActivityTest {
  @Test
  public void EmptyTest(){}

    @Rule
    public IntentsTestRule<GroupActivity>  mManualRule =
            new IntentsTestRule<>(GroupActivity.class, false, false);

    @Rule
    public myRule mNotAdmin = new myRule(GroupActivity.class, true);

    @Rule
    public myRule mWrongInputInBundle = new myRule(GroupActivity.class, false);

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
    public void AdminCanLeadToCreateMeeting(){
        mManualRule.launchActivity(new Intent());
        testIntent(R.id.createMeeting, CreateMeetingActivity.class.getName());
        mManualRule.finishActivity();
    }

    @Test
    public void leadsToInviteFriends(){
        mManualRule.launchActivity(new Intent());
        testIntent(R.id.invite_friends, InviteFriendsActivity.class.getName());
        mManualRule.finishActivity();
    }

    /*@Test
    public void leadsToCalendar(){
        mManualRule.launchActivity(new Intent());
        testIntent(R.id.calendarBtn, ConnectedCalendarActivity.class.getName());
        mManualRule.finishActivity();
    }


    @Test
    public void NoAdminCantLeadToCreateMeeting(){
        mNotAdmin.launchActivity(new Intent());
        onView(withId(R.id.m)).check(matches(not(isEnabled())));
        mNotAdmin.finishActivity();
    }

    */
    /*@Test
    public void leadsToNavigationActivity(){
        GroupActivity mActivity = mWrongInputInBundle.launchActivity(new Intent());
        assertTrue(mActivity.getInfoWrongInput());
        mWrongInputInBundle.finishActivity();
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



    private class myRule extends IntentsTestRule<GroupActivity>{
        private boolean b;
        private final Bundle bundle = new Bundle();

        public myRule(Class<GroupActivity> activityClass, boolean initialTouchMode, boolean launchActivity) {
            super(activityClass, initialTouchMode, launchActivity);
        }

        public myRule(Class<GroupActivity> activityClass, boolean noAdmin){
            this(activityClass, false, false);
            this.b = noAdmin;
        }

        @Override
        protected void beforeActivityLaunched(){
            super.beforeActivityLaunched();
            if(b){
                bundle.putString(Messages.ADMIN, "");
            }else{
                bundle.putInt(Messages.maxUser, -1);
            }
            GlobalBundle.getInstance().putAll(bundle);
        }

        @Override
        protected void afterActivityFinished(){
            super.afterActivityFinished();
            if(b){
                bundle.putString(Messages.ADMIN, Messages.TEST);
            }else{
                bundle.putInt(Messages.maxUser, 1);
            }
            GlobalBundle.getInstance().putAll(bundle);
        }



    }

}
