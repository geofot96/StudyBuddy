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
import ch.epfl.sweng.studdybuddy.activities.*;

@RunWith(AndroidJUnit4.class)
public class GoogleSignInTest {
    @Rule
    public IntentsTestRule<DummyGoogleSignInActivity> DummyGoogleSignInActivityIntentRule =
            new IntentsTestRule<>(DummyGoogleSignInActivity.class, false, false);

    @Rule
    public IntentsTestRule<DummyMainActivity> DummyMainActivityIntentRule =
            new IntentsTestRule<>(DummyMainActivity.class, false, false);

    @Test
    public void LoginShouldGoToMainActivity(){
        DummyGoogleSignInActivityIntentRule.launchActivity(new Intent());
        onView(withId(R.id.googleBtn)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), MainActivity.class)));
    }

    @Test
    public void logoutShouldGoToGoogleSignInActivity(){
        DummyMainActivityIntentRule.launchActivity(new Intent());
        onView(withId(R.id.signout_btn)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), GoogleSignInActivity.class)));
    }
}
