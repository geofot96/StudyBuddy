package ch.epfl.sweng.studdybuddy.util;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.tools.GroupsRecyclerAdapter;

/**
 * A specialised filter used when typing a course's name in the feed
 */
public class FeedFilter extends Filter {
    GroupsRecyclerAdapter adapter;
    private List<Group> filterList;

    /**
     * Constuctor for the feed linked to an adapter containing a list of groups
     * @param adapter the adapter to be used with this filter
     * @param filterList the initial list of groups related to the feed
     */
    public FeedFilter(GroupsRecyclerAdapter adapter, List<Group> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    /**
     * Perform filtering on the current list
     * @param constraint the character sequence to be pattern matched with the available list
     * @return the resulting list after applying the filter
     */
    public List<Group> forceFiltering(CharSequence constraint) {
        return (List<Group>) performFiltering(constraint).values;
    }

    /**
     * Perform filtering using a partial character sequence
     * @param constraint the character sequence to be pattern matched with the available list
     * @return the result after applying the filter
     */
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint == null || constraint.length() == 0) {
            results.count = filterList.size();
            results.values = filterList;
            return results;
        }
        constraint = constraint.toString().toUpperCase();
        ArrayList<Group> filteredGroup = new ArrayList<>();
        for (int i = 0; i < filterList.size(); i++) {
            if (filterList.get(i).getCourse().getCourseName().toUpperCase().contains(constraint)) { //ADD PLAYER TO FILTERED PLAYERS
                filteredGroup.add(filterList.get(i));
            }
        }
        results.count = filteredGroup.size();
        results.values = filteredGroup;
        return results;
    }

    /**
     * sets the newly filtered list to the adapter and notifies other elements
     * @param constraint the character sequence to be pattern matched with the available list
     * @param results the result of the filtering
     */
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setGroupList((List<Group>) results.values);
        //REFRESH
        adapter.notifyDataSetChanged();
    }

    /**
     * used for filtering out full groups
     * @param newfilterList the new filter to be applied
     */
    public void setFilterList(List<Group> newfilterList) {
        this.filterList = newfilterList;
    }
}
