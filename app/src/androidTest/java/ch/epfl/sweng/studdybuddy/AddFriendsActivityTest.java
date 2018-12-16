package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.AddFriendsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class AddFriendsActivityTest {

    public static AddFriendsActivity addFriendsActivity;
    public static final String buddy = "Bou";

    @Rule
    public final IntentsTestRule<AddFriendsActivity> mActivityRule =
            new IntentsTestRule<>(AddFriendsActivity.class);
    @Before
    public void goToActivity() {
        addFriendsActivity =  (AddFriendsActivity) mActivityRule.getActivity();
    }

    @Test
    public void EveryThingIsDisplayed() {
        onView(withId(R.id.text_add_friends)).check(matches(isDisplayed()));
        onView(withId(R.id.friendsComplete)).check(matches(isDisplayed()));
        onView(withId(R.id.friends_set)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_add)).check(matches(isClickable()));
    }


    @Test //()
    public void friendsNotAddedIfGibberish () throws InterruptedException {
    onView(withId(R.id.friendsComplete)).perform(click(), typeText("Nobody"));
    Thread.sleep(1000);
    onView(withId(R.id.btn_add)).check(matches(not(isEnabled())));
}

/*
@Test
public void clickOnFriendsSuggestionAddsFriendToList() throws InterruptedException {
    onView(withId(R.id.friendsComplete)).perform(click(), typeText(buddy));
    onData(equalTo(buddy)).inRoot(RootMatchers.isPlatformPopup()).perform(click());
    onView(withId(R.id.friendsComplete)).perform(closeSoftKeyboard());
    Thread.sleep(500);
    onView(withId(R.id.friends_set)).check(matches(hasDescendant(withText(buddy))));
}*/

}




