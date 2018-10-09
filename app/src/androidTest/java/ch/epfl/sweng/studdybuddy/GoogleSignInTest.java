package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class GoogleSignInTest {
    @Rule
    public final ActivityTestRule<GoogleSignInActivity> mActivityRule =
            new ActivityTestRule<>(GoogleSignInActivity.class);

    @Test
    public void loginOnSuccessShouldGoToMainActivity(){

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
