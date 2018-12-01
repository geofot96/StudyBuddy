package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.auth.GoogleSignInActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sweng.studdybuddy.NavigationTestHelper.navigate;

@RunWith(AndroidJUnit4.class)
public class SettingsFragmentTest {

    @Rule
    public IntentsTestRule<NavigationActivity> mActivityTestRule = new IntentsTestRule <>(NavigationActivity.class);

    @Rule public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void goToSettings(){
        navigate("Settings", R.id.navToSettings,3);
    }

    @Test
    public void defaultLocationLaunchesMapsActivity(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.defaultLocation)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(MapsActivity.class.getName()));
    }

    @Test
    public void signoutWorks() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.btn_sign_out)).perform(click());
        Thread.sleep(2000);
        intended(hasComponent(GoogleSignInActivity.class.getName()));
    }





}