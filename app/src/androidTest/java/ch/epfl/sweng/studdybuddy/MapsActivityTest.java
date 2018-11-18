package ch.epfl.sweng.studdybuddy;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import ch.epfl.sweng.studdybuddy.activities.MapsActivity;

public class MapsActivityTest {

    @Rule
    public IntentsTestRule<MapsActivity>  mActivity=
            new IntentsTestRule<>(MapsActivity.class);

    @Test
    public void test(){

    }
}
