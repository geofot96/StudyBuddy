package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.ConnectedCalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupInfoActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class GroupActivityTest {
    private Intent intent;

    @Rule
    public myRule mActivityRule =
            new myRule(GroupActivity.class);

    @Test
    public void leadsToCalendar(){
        testIntent(R.id.calendarBtn, ConnectedCalendarActivity.class.getName());
    }

    @Test
    public void leadsToParticipants(){
        testIntent(R.id.participantsBtn, GroupInfoActivity.class.getName());
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

        @Override
        public GroupActivity launchActivity(@Nullable Intent startIntent) {
            intent = new Intent();
            intent.putExtra(Messages.maxUser, 1);
            intent.putExtra(Messages.userID, Messages.TEST);
            intent.putExtra(Messages.groupID, Messages.TEST);
            return super.launchActivity(intent);
        }
    }
}
