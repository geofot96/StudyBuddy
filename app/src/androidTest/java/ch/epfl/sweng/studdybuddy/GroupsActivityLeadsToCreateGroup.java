package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GroupsActivityLeadsToCreateGroup
{

    @Rule
    public ActivityTestRule<GroupsActivity> mActivityTestRule = new ActivityTestRule(GroupsActivity.class);


    @Test
    public void addGroupButtonExists()
    {
       // refactor();
        ViewInteraction linearLayout = onView(withId(R.id.createGroup));
        linearLayout.check(matches(isDisplayed()));
    }


    @Test
    public void searchBarExists()
    {
       // refactor();
        ViewInteraction linearLayout = onView(withId(R.id.feed_search));
        linearLayout.check(matches(isDisplayed()));
    }
    @Test
    public void recyclerViewerExists()
    {
      //  refactor();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction linearLayout = onView(withId(R.id.feedRecycleViewer));
        linearLayout.check(matches(isDisplayed()));
    }
  /*  @Test
    public void sortButtonExists()
    {
        refactor(); //TODO update the UI so that the sorting button is never hidden
        ViewInteraction linearLayout = onView(withId(R.id.sortButton));

        try{
            Thread.sleep(2000);
        }catch (InterruptedException e ){
            e.printStackTrace();
        }

        linearLayout.check(matches(isDisplayed()));
    }
*/
    void refactor(){
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.gotoGroups), withText("Groups"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());
    }

    static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position)
    {

        return new TypeSafeMatcher<View>()
        {
            @Override
            public void describeTo(Description description)
            {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view)
            {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}