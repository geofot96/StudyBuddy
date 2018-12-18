package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.CourseSelectActivity;
import ch.epfl.sweng.studdybuddy.auth.DummyGoogleSignInActivity;
import ch.epfl.sweng.studdybuddy.auth.DummyNavigationActivity;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class GoogleSignInTest {
  @Test
  public void EmptyTest(){}
    @Rule
    public IntentsTestRule<DummyGoogleSignInActivity> DummyGoogleSignInActivityIntentRule =
            new IntentsTestRule<>(DummyGoogleSignInActivity.class, false, false);

    @Rule
    public IntentsTestRule<DummyNavigationActivity> DummyNavigationActivityIntentRule =
            new IntentsTestRule<>(DummyNavigationActivity.class, false, false);

    @Test
    public void SignInTest() {
        try{
        interaction(DummyGoogleSignInActivityIntentRule, R.id.googleBtn, CourseSelectActivity.class);
        }catch(InterruptedException e){

        }
    }

    public <T extends Activity, A extends Activity> void interaction(IntentsTestRule<T> rule, int button, Class<A> expected) throws InterruptedException {
        rule.launchActivity(new Intent());
        onView(withId(button)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), expected)));
        rule.finishActivity();
    }
}
