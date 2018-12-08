package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.Fragments.SettingsFragment;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.SettingsFragmentHelper;

import static android.app.Activity.RESULT_OK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static ch.epfl.sweng.studdybuddy.NavigationTestHelper.navigate;
import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class SettingsFragmentTest {

    @Rule
    public IntentsTestRule<NavigationActivity> mActivityTestRule = new IntentsTestRule<>(NavigationActivity.class);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule .grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    public static SettingsFragment fragment;


    @Before
    public void goToSettings() {
        MapsActivityTest.allowPermissionsIfNeeded(android.Manifest.permission.ACCESS_FINE_LOCATION);
        navigate("Settings", R.id.navToSettings, 3);
        fragment =  (SettingsFragment) mActivityTestRule.getActivity().getSupportFragmentManager().findFragmentByTag("mainFragment");
    }

    @Test
    public void defaultLocationLaunchesMapsActivity() {

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.text_location_set_up)).perform(click());

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(MapsActivity.class.getName()));
    }

   @Test
    public void EveryThingIsDisplayed() {
        onView(withId(R.id.text_favorite_language)).check(matches(isDisplayed()));
        onView(withId(R.id.text_favorite_location)).check(matches(isDisplayed()));
        onView(withId(R.id.spinner_languages_settings)).check(matches(isClickable()));
        onView(withId(R.id.btn_settings_apply)).check(matches(isClickable()));
        onView(withId(R.id.btn_sign_out)).check(matches(isClickable()));
        onView(withId(R.id.text_location_set_up)).check(matches(isClickable()));
    }
    @Test
    public void TriggerOnNothingSelectedDoesNothing(){
        fragment.onNothingSelected(null);
    }
    @Test
    public void onResultTest() throws Throwable {

        Bundle bundle =  GlobalBundle.getInstance().getSavedBundle();
        bundle.putString(Messages.LOCATION_TITLE, "");
        bundle.putString(Messages.ADDRESS, "");
        bundle.putDouble(Messages.LATITUDE, 0);
        bundle.putDouble(Messages.LONGITUDE, 0);
        GlobalBundle.getInstance().putAll(bundle);
        sleep(3000);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragment.onActivityResult(1, RESULT_OK,new Intent());

            }
        });
        onView(withId(R.id.text_location_set_up)).check(matches(withText("Default Location: : ")));
    }

    @Test
    public void signoutButtonTest() throws InterruptedException {
        onView(withId(R.id.btn_sign_out)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.googleBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void applyButton() throws Throwable {
        new SettingsFragmentHelper();
        Thread.sleep(1000);
     //   onView(withId(R.id.btn_settings_apply)).perform(click());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button apply = mActivityTestRule.getActivity().findViewById(R.id.btn_settings_apply);

                apply.performClick();

            }
        });
        onView(withId(R.id.btn_settings_apply)).check(matches(isDisplayed()));
    }
}
