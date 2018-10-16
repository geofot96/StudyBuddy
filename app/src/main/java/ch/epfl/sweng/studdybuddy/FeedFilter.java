package ch.epfl.sweng.studdybuddy;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class FeedFilter extends Filter {
    GroupsRecyclerAdapter adapter;
    private List<Group>  filterList;

    public FeedFilter(GroupsRecyclerAdapter adapter, List<Group> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Group> filteredGroup=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getCourse().getCourseName().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredGroup.add(filterList.get(i));
                }
            }

            results.count=filteredGroup.size();
            results.values=filteredGroup;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setGroupList((List<Group>) results.values);

        //REFRESH
        adapter.notifyDataSetChanged();

    }
}
