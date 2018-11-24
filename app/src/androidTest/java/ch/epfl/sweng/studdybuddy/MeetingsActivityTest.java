package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MeetingsActivityTest {

    @Rule
    public MeetingsRule mActivityRule = new MeetingsRule(MeetingsActivity.class);

    @BeforeClass
    public static void setup(){
        Bundle bundle = new Bundle();
        bundle.putString(Messages.groupID, Messages.TEST);
        bundle.putString(Messages.ADMIN, Messages.TEST);
        GlobalBundle.getInstance().putAll(bundle);
    }

    @Test
    public void noCardViewInFeedback(){
        onView(withId(R.id.meetingRV)).check(matches(isDisplayed()));
    }


    private class MeetingsRule extends ActivityTestRule<MeetingsActivity> {
        Intent intent = new Intent();
        public MeetingsRule(Class<MeetingsActivity> activityClass) {
            super(activityClass);
            intent.putExtra(Messages.groupID, Messages.TEST);
            intent.putExtra(Messages.ADMIN, Messages.TEST);
        }

        @Override
        public MeetingsActivity launchActivity(Intent intent){
            return super.launchActivity(this.intent);
        }
    }

}
