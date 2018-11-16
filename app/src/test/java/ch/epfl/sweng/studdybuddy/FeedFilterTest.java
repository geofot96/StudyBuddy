package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.util.FeedFilter;
import ch.epfl.sweng.studdybuddy.tools.GroupsRecyclerAdapter;

import static ch.epfl.sweng.studdybuddy.util.CoreFactory.groups2;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

public class FeedFilterTest {
    List<Group> groups = groups2();
    GroupsRecyclerAdapter ad = mock(GroupsRecyclerAdapter.class);
    FeedFilter filter = new FeedFilter(ad, groups);
    @Test
    public void performFilteringWithNoConstraints() {
        List<Group> fr = filter.forceFiltering("");
        assertEquals(groups.size(), fr.size());
    }

    @Test
    public void performFilteringWithLength1Constraint() {
        List<Group> results = filter.forceFiltering("alg");
        assertEquals(2, results.size());
    }

    @Test
    public void performFilteringWithNoResults() {
        List<Group> fr = filter.forceFiltering("lnajvdbdsonvnd");
        assertEquals(0, fr.size());
    }

    @Test
    public void performFilteringWithNullConstraint() {
        List<Group> res = filter.forceFiltering(null);
        assertEquals(groups.size(), res.size());
    }
}
