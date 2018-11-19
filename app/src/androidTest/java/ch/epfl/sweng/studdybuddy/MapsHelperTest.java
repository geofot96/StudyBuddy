package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.MapsActivity;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.MeetingLocation;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

public class MapsHelperTest {

    @Rule
    public IntentsTestRule<MapsActivity> mIntentsTestRule = new IntentsTestRule<>(MapsActivity.class);

    @Test
    public void acceptMeetingsTest(){
        List<Meeting> meetings, meetingsFb;
        meetings = new ArrayList<>();
        Meeting m = new Meeting("a");
        m.setLocation(new MeetingLocation("a", "b", 1,2));
        meetingsFb = new ArrayList<Meeting>();
        meetingsFb.add(m);
        onView(withId(R.id.confirmLocation)).check(matches(not(ViewMatchers.isDisplayed())));


    }
}
