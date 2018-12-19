package ch.epfl.sweng.studdybuddy;

import android.content.ComponentName;
import android.os.Bundle;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.CardView;
import android.widget.GridLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.studdybuddy.activities.group.ConnectedCalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.GroupActivity;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConcreteAvailability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedCalendar;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)

public class ConnectedCalendarActivityTest{

    private GridLayout calendarView;

    private ConnectedCalendar calendar;

    private ConnectedCalendarActivity activity;

    @Rule
    public myRule mActivityRule =
            new myRule(ConnectedCalendarActivity.class);


    @Before
    public void setUp(){
        Map<String, List<Boolean>> mapAvailabailities = new HashMap<>();
        mapAvailabailities.put("test", mActivityRule.getMockAvailabilities());
        calendar = new ConnectedCalendar(mapAvailabailities);
        calendarView = mActivityRule.getActivity().findViewById(R.id.calendarGrid);
    }

    @Test
    public void confirmButtonIsClikable(){
        onView(withId(R.id.confirmSlots)).perform(scrollTo(), click());
        onView(withId(R.id.editAvail)).check(matches(isDisplayed()));
    }

    @Test
    public void seeAvailabilitiyInFirstTimeSlot() throws Throwable {
        activity = mActivityRule.getActivity();
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              activity.update(calendar);
                          }
                      }
        );
        CardView cardView = (CardView) calendarView.getChildAt(1);
        boolean rightColors = (cardView.getCardBackgroundColor().getDefaultColor() == -16711936) && checkAreWhite(2);
        assertTrue(rightColors);
    }

    @Test
    public void addAvailabilityInTimeSlot() throws Throwable{
       assertEquals(-16711936, setAvailability(2));
    }

    @Test
    public void removeAvailabilityInTimeSlot() throws Throwable{
        assertEquals(-1, setAvailability(1));
    }


    /**
     * check if every cell of the calendar, from <tt>start</tt> to
     * the end of the grid has a white background
     * @param start the index head of the sublist of the calendar we want to test
     * @return <tt>true</tt> if every cell from <tt>start</tt> are white
     */
   private boolean checkAreWhite(int start) {
        CardView cardView;
        for (int i = start; i < 88; i += (i % 8 == 7) ? 2 : 1) {
                cardView = (CardView) calendarView.getChildAt(i);
                if (cardView.getCardBackgroundColor().getDefaultColor() != -1) {
                    return false;
                }
        }
        return true;
    }

    /**
     * perform a click on a targeted cardiew
     * @param cardView
     * @throws Throwable
     */
    private void clickOnCardView(final CardView cardView) throws Throwable {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cardView.performClick();
            }
        });
    }

    private int setAvailability(int index) throws Throwable{
        CardView cardView = (CardView) calendarView.getChildAt(index);
        try{
            clickOnCardView(cardView);
        }catch (Throwable e){
            e.printStackTrace();
        }

        activity = mActivityRule.getActivity();
        calendar.modify("test", activity.getUserAvailabilities().getUserAvailabilities());
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                activity.update(calendar);
            }
        });
        return cardView.getCardBackgroundColor().getDefaultColor();
    }


    private class myRule extends ActivityTestRule<ConnectedCalendarActivity> {
        private List<Boolean> mockAvailabilities = new ArrayList<>();

        public myRule(Class<ConnectedCalendarActivity> activityClass) {
            super(activityClass);
        }

        @Override
        protected void beforeActivityLaunched() {
            super.beforeActivityLaunched();
            setUpMockAvailaibilities();
            GroupActivityTest.setup();
            Bundle bundle = new Bundle();
            bundle.putString(Messages.groupID, "this is a test");
            GlobalBundle.getInstance().putAll(bundle);
        }

        private void setUpMockAvailaibilities() {
            mockAvailabilities.clear();
            for (int i = 0; i < ConnectedCalendar.CALENDAR_SIZE; i++) {
                mockAvailabilities.add(false);
            }
            mockAvailabilities.set(0, true);
        }

        @Override
        protected void afterActivityLaunched(){
            ConnectedCalendarActivity activity = getActivity();
            Availability availability = new ConcreteAvailability(mockAvailabilities);
            activity.setUserAvailabilities(availability);
        }

        public List<Boolean> getMockAvailabilities() {
            if(mockAvailabilities.size()< ConnectedCalendar.CALENDAR_SIZE){
                setUpMockAvailaibilities();
            }
            return mockAvailabilities;
        }
    }

}
