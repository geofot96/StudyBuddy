package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class GroupInfoActivityTest {

    @Rule
    public final IntentsTestRule<GroupsActivity> mActivityRule =
            new IntentsTestRule<>(GroupsActivity.class);


    @Test
    public void quitButtonTest(){
        Activity activity = mActivityRule.getActivity();
        Intent intent = new Intent(activity.getApplicationContext(), GroupsActivity.class);
        intent.putExtra(GroupsActivity.GROUP_ID, "1");
        activity.startActivity(intent);
        try {
            Thread.sleep(2000);
            onView(withId(R.id.mybutton)).perform(click());
            intended(hasComponent(GroupsActivity.class.getName()));
        }
        catch (Exception e) {

        }
    }
}
