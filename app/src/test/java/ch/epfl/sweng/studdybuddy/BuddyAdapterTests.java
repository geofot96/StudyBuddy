package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.BuddyAdapter;
import ch.epfl.sweng.studdybuddy.tools.BuddyHolder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class BuddyAdapterTests
{
    List<User> participants = Arrays.asList(new User("a", "b"));

    @Test
    public void bindViewHolderTest() {
        BuddyHolder h = mock(BuddyHolder.class);
        BuddyAdapter adapter = new BuddyAdapter(participants, new ID<>(), new ArrayList<>(), 5);


        adapter.onBindViewHolder(h, 0);
    }

    @Test
    public void testGetItemCount() {
        BuddyHolder h = mock(BuddyHolder.class);

        ArrayList<User> users = new ArrayList<>();
        users.add(new User("a", "b"));

        BuddyAdapter adapter = new BuddyAdapter(users, new ID<>(), new ArrayList<>(), 5);

        assertEquals(1, adapter.getItemCount());

        users.add(new User("seconc", "user"));
        assertEquals(2, adapter.getItemCount());
    }
}
