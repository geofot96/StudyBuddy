package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.meetings.MeetingsActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MeetingsActivityTest {

    @Rule
    public MeetingsRule mActivityRule = new MeetingsRule(MeetingsActivity.class);

    @Before

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
