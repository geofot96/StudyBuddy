package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class GoogleSignOutTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void onSetUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void SimulateAlreadyLoggedIn(){
        AuthManager SpiedMAuth = Mockito.spy(mActivityRule.getActivity().getmAuth());
        Mockito.when(SpiedMAuth.getCurrentUser()).thenReturn(truc());
        mActivityRule.getActivity().setmAuth(SpiedMAuth);
    }

    public Account truc(){
        System.out.println("stop user");
        return new Account();
    }

    @Test
    public void logoutShouldGoToGoogleSignInActivity(){
        onView(withId(R.id.signout_btn)).perform(click());
        intended(hasComponent(GoogleSignInActivity.class.getName()));
    }
}
