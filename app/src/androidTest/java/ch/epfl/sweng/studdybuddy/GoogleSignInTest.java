package ch.epfl.sweng.studdybuddy;

import android.content.ComponentName;
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
public class GoogleSignInTest {
    @Rule
    public IntentsTestRule<DummyGoogleSignInActivity> gIntentRule =
            new IntentsTestRule<>(DummyGoogleSignInActivity.class);


    @Test
    public void LoginShouldGoToMainActivity(){
        onView(withId(R.id.googleBtn)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), MainActivity.class)));
    }

}
