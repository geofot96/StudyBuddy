package ch.epfl.sweng.studdybuddy;

import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;


import org.junit.BeforeClass;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;


import ch.epfl.sweng.studdybuddy.activities.group.GroupInfoActivity;

import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;

import ch.epfl.sweng.studdybuddy.tools.Adapter;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AdapterTest {

    @Rule

    public final IntentsTestRule<GroupInfoActivity> mActivityRule =
            new IntentsTestRule<>(GroupInfoActivity.class);

    @Test
    public void initRecyclerView() {

        Adapter ad = new ParticipantAdapter(new ArrayList<>());
        Context ctx = mActivityRule.getActivity().getApplicationContext();
        RecyclerView rv = new RecyclerView(ctx);
        ad.initRecyclerView(ctx, rv);
        assertTrue(rv.getAdapter().equals(ad));
    }
}
