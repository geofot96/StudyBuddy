package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.util.FeedFilter;
import ch.epfl.sweng.studdybuddy.tools.GroupsRecyclerAdapter;

import static ch.epfl.sweng.studdybuddy.tools.AdapterConsumer.searchListener;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListenerFactoryTest {
    @Test
    public void searchListenerReturnsFalse() {
        GroupsRecyclerAdapter ad = mock(GroupsRecyclerAdapter.class);
        FeedFilter f = mock(FeedFilter.class);
        when(ad.getFilter()).thenReturn(f);
        assertFalse(searchListener(ad).onQueryTextChange(""));
        //assertFalse(searchListener(ad).onQueryTextSubmit(""));
    }
}
