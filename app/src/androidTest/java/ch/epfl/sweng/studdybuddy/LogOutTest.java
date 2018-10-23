package ch.epfl.sweng.studdybuddy;

import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LogOutTest {
    @Rule
    public IntentsTestRule<DummyMainActivity> mActivityRule =
            new IntentsTestRule<>(DummyMainActivity.class, false, false);

    @Test
    public void logoutLeadGoogleSignInActivity(){
        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.signout_btn)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), GoogleSignInActivity.class)));
    }
}

