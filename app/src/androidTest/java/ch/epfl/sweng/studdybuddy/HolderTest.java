package ch.epfl.sweng.studdybuddy;

import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.GroupInfoActivity;
import ch.epfl.sweng.studdybuddy.util.Holder;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)
public class HolderTest {

    @Rule
    public final IntentsTestRule<GroupInfoActivity> mActivityRule =
            new IntentsTestRule<>(GroupInfoActivity.class);

    @Test
    public void bindWorks(){
        Context ctx = mActivityRule.getActivity().getApplicationContext();
        TextView txtView = new TextView(ctx);
        View itemView = mock(View.class);
        when(itemView.findViewById(0)).thenReturn(txtView);
        Holder holder = new Holder(itemView, 0);
        holder.bind("testing");
        assertTrue(txtView.getText().toString().equals("testing"));

    }
}
