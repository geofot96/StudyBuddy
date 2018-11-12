package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.GridLayout;

import com.google.android.gms.tasks.Task;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.ConnectedCalendarActivity;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sweng.studdybuddy.GroupsActivityLeadsToCreateGroup.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)

public class ConnectedCalendarActivityTest{
    private Intent intent;
    private GridLayout calendar;

    @Rule
    public myRule mActivityRule =
            new myRule(ConnectedCalendarActivity.class);


    @Before
    public void setUp(){
        calendar = mActivityRule.getActivity().findViewById(R.id.calendarGrid);
    }

    @Test
    public void seeAvailabilitiyInFirstTimeSlot(){
        CardView cardView = (CardView) calendar.getChildAt(1);
        boolean rightColors = (cardView.getCardBackgroundColor().getDefaultColor() == -16711936) && checkAreWhite(2);
        assertTrue(rightColors);
    }

    @Test
    public void addAvailabilityInTimeSlot(){
        ViewInteraction gridLayout = onView(
                allOf(withId(R.id.calendarGrid),
                        childAtPosition(
                                allOf(withId(R.id.generalThing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                1),
                        isDisplayed()));
        gridLayout.check(matches(isDisplayed()));
        settingTime();
        ClickOnSlot(2);
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        CardView cardView = (CardView) calendar.getChildAt(2);
        assertEquals(-16711936, cardView.getCardBackgroundColor().getDefaultColor());
    }

    @Test
    public void removeAvailabilityInTimeSlot(){

        ViewInteraction gridLayout = onView(
                allOf(withId(R.id.calendarGrid),
                        childAtPosition(
                                allOf(withId(R.id.generalThing),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                1),
                        isDisplayed()));
        gridLayout.check(matches(isDisplayed()));

        settingTime();
        ClickOnSlot(1);
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        CardView cardView = (CardView) calendar.getChildAt(1);
        assertEquals(-1, cardView.getCardBackgroundColor().getDefaultColor());
    }



    private boolean checkAreWhite(int start){
        CardView cardView;
        for(int i = start; i<88; i++){
            if(i%8 != 0) {
                cardView = (CardView) calendar.getChildAt(i);
                if (cardView.getCardBackgroundColor().getDefaultColor() != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void ClickOnSlot(int position){
        ViewInteraction timeSlot = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.calendarGrid),
                                childAtPosition(
                                        withId(R.id.generalThing),
                                        1)),
                        position),
                        isDisplayed()));
        timeSlot.perform(click());
    }

    private void settingTime(){
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private class myRule extends ActivityTestRule<ConnectedCalendarActivity> {


        public myRule(Class<ConnectedCalendarActivity> activityClass) {
            super(activityClass);
        }

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            List<Boolean> list = new ArrayList<>();
            for (int i = 0; i < 77; i++) {
                list.add(false);
            }
            list.set(0, true);
            FirebaseReference fb = new FirebaseReference();
            Task<Void> task = fb.select("availabilities").select(Messages.TEST).select(Messages.TEST).setVal(list);
            while (!(task.isComplete())) ;
        }

        @Override
        public ConnectedCalendarActivity launchActivity(@Nullable Intent startIntent){
            intent = new Intent();
            intent.putExtra(Messages.maxUser, 1);
            intent.putExtra(Messages.userID, Messages.TEST);
            intent.putExtra(Messages.groupID, Messages.TEST);
            return super.launchActivity(intent);
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
