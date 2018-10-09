package ch.epfl.sweng.studdybuddy;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.Matchers.not;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

@RunWith(AndroidJUnit4.class)
public class GoogleSignInTest {
    @Rule
    public IntentsTestRule<GoogleSignInActivity> mGoogleSignInRule = new IntentsTestRule<>(
            GoogleSignInActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void loginOnSuccessShouldGoToMainActivity(){
        onView(withId(R.id.googleBtn)).perform(click());
        Intent intent = new Intent();
        GoogleSignInAccount user = Mockito.mock(GoogleSignInAccount.class);
        intent.putExtra("user", user);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(1, intent);
        intending(toPackage("com.google.android.gms.auth.api.signin.GoogleSignIn")).respondWith(result);

        intended(toPackage(MainActivity.class.toString()));
    }

    @Test
    public void alreadyLoggedInShouldBeOnMainActivity(){

    }

    @Test
    public void logoutOnSuccessShouldGoToGoogleSignInActivity(){

    }

    @Test
    public void LogInOnFailureShouldStayOnGoogleSignInActivity(){

    }
}
