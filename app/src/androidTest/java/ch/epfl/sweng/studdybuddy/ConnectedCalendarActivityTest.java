package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.CardView;
import android.widget.GridLayout;

import com.google.android.gms.tasks.Task;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.group.ConnectedCalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)

public class ConnectedCalendarActivityTest{
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
       assertEquals(-16711936, setAvailability(2));
    }

    @Test
    public void removeAvailabilityInTimeSlot(){
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
                cardView = (CardView) calendar.getChildAt(i);
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

    private int setAvailability(int index){
        CardView cardView = (CardView) calendar.getChildAt(index);
        try{
            clickOnCardView(cardView);
        }catch (Throwable e){
            e.printStackTrace();
        }

        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return cardView.getCardBackgroundColor().getDefaultColor();
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
            Task<Void> task = fb.select(Messages.FirebaseNode.AVAILABILITIES).select(Messages.TEST).select(Messages.TEST).setVal(list);
            while (!(task.isComplete())) ;
            GroupActivityTest.setup();
        }
    }
}
