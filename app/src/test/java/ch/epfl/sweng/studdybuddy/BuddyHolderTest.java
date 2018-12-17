package ch.epfl.sweng.studdybuddy;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.junit.Before;
import org.junit.Test;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.tools.BuddyHolder;

import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuddyHolderTest
{
    private View v;
    private  TextView buddy;
    private Button invite;
    private BuddyHolder holder;

    @Before
    public void test()
    {
        v = mock(View.class);
        buddy = mock(TextView.class);
        invite = mock(Button.class);

        when(v.findViewById(R.id.buddy)).thenReturn(buddy);
        when(v.findViewById(R.id.invite)).thenReturn(invite);
        when(buddy.getText()).thenReturn("name");

        holder = new BuddyHolder(v);
    }

    @Test
    public void testBindTrue()
    {
        holder.bind("name", true, new ID<Group>(), new ID<>());
        verify(buddy, times(1)).setText("name");
        verify(invite, times(1)).setText("Invite");
    }

    @Test
    public void testBindFalse()
    {
        holder.bind("name", false, new ID<Group>(), new ID<>());
        verify(invite, times(1)).setEnabled(false);
        verify(invite, times(1)).setVisibility(View.INVISIBLE);
    }

    @Test
    public void testGet()
    {
        buddy.setText("name");
        assertEquals("name", holder.get());
    }

    @Test
    public void testClick()
    {
        //holder.bind("name", true, new ID<Group>(), new ID<>());
        //holder.getInvitationListener().onClick(v);
        //TODO mock the reference
//        when(invite.performClick()).thenCallRealMethod();
//        invite.performClick(); //TODO test click
    }

}
