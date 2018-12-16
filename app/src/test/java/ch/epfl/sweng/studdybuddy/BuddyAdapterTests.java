package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.BuddyAdapter;
import ch.epfl.sweng.studdybuddy.tools.BuddyHolder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuddyAdapterTests
{
    List<User> participants = Arrays.asList(new User("a", "b"));

    @Test
    public void bindViewHolderTest() {
        BuddyHolder h = mock(BuddyHolder.class);
        BuddyAdapter adapter = new BuddyAdapter(participants, new ID<>(), new ID<>());


        adapter.onBindViewHolder(h, 0);
    }

    @Test
    public void testGetItemCount() {
        BuddyHolder h = mock(BuddyHolder.class);

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("a", "b"));

        BuddyAdapter adapter = new BuddyAdapter(users, new ID<>(), new ID<>());

        assertEquals(1, adapter.getItemCount());

        users.add(new User("seconc", "user"));
        assertEquals(2, adapter.getItemCount());
    }
}
