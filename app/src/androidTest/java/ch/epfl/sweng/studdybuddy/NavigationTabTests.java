package ch.epfl.sweng.studdybuddy;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;

@RunWith(AndroidJUnit4.class)
public class NavigationTabTests
{

    @Rule
    public ActivityTestRule<NavigationActivity> mActivityTestRule = new ActivityTestRule<>(NavigationActivity.class);


    @Test
    public void testClickOnHome()
    {

        NavigationTestHelper.navigate("Home", R.id.navToHome, 0);
    }

    @Test
    public void testClickOnProfile()
    {
        NavigationTestHelper.navigate("Profile", R.id.navToProfile, 1);
    }

    @Test
    public void testClickOnChat()
    {
        NavigationTestHelper.navigate("Chat", R.id.navToChat, 2);
    }
}
