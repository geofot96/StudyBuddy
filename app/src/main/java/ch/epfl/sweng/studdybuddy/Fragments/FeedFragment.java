package ch.epfl.sweng.studdybuddy.Fragments;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.CalendarActivity;
import ch.epfl.sweng.studdybuddy.activities.CreateGroupActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupsActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.util.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.util.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment
{

    GroupsRecyclerAdapter mAdapter;
		static List<Group> groupSet  = new ArrayList<>();
    public static final String GROUP_ID = "ch.epfl.sweng.studdybuddy.groupId";
    public static final String IS_PARTICIPANT ="ch.epfl.sweng.studdybuddy.particip";
    static List<Group> filteredGroupSet = new ArrayList<>();
    Button sortButton;
    FloatingActionButton actionButton;

    public FeedFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.feedRecycleViewer);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        FirebaseReference firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
        String userId = ((StudyBuddy) getActivity().getApplication()).getAuthendifiedUser().getUserID().toString();

        Consumer<Object> consumer = getConsumer(v);
        mAdapter = new GroupsRecyclerAdapter(groupSet, userId, consumer);
        rv.setAdapter(mAdapter);
        firebase.select("groups").getAll(Group.class, AdapterConsumer.adapterConsumer(Group.class, groupSet, new RecyclerAdapterAdapter(mAdapter)));
        SearchView sv = (SearchView) v.findViewById(R.id.feed_search);
        sv.onActionViewExpanded();
        sv.clearFocus();
        sv.setOnQueryTextListener(getListener());
        setUpActivity(rv, sv);
        sortButton = v.findViewById(R.id.sortButton);

        sortButton.setOnClickListener(getOnClickListener());

        actionButton = v.findViewById(R.id.createGroup);

        actionButton.setOnClickListener(getFloatingButtonListener());
/*
        ToggleButton toggleFull = (ToggleButton) findViewById(R.id.toggleButton);
        toggleFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { toggleButtonFullBehaviour(buttonView,isChecked);}});

*/
        return v;
    }

    public void toggleButtonFullBehaviour(CompoundButton buttonView, boolean isChecked)
    {
        if (isChecked) {
            filteredGroupSet.clear();
            selectOnlyAvailableGroups();
            mAdapter.setGroupList(filteredGroupSet);
            mAdapter.setFilterList(filteredGroupSet);
        } else {
            mAdapter.setGroupList(groupSet);
            mAdapter.setFilterList(groupSet);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void selectOnlyAvailableGroups() {
        for (Group g : groupSet) {
            if (g.getMaxNoUsers() > mAdapter.getParticipantNumber(g))
                filteredGroupSet.add(g);
        }
    }
    @NonNull
    private View.OnClickListener getFloatingButtonListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), CreateGroupActivity.class);
                startActivity(intent);
            }
        };
    }

    private void setUpActivity(RecyclerView rv, SearchView sv) {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
        sv.onActionViewExpanded();
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @NonNull
    private View.OnClickListener getOnClickListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sortGroupCards(v);
            }
        };
    }

    @NonNull
    private SearchView.OnQueryTextListener getListener()
    {
        return new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {
                mAdapter.getFilter().filter(query); //FILTER AS YOU TYPE
                return false;
            }
        };
    }

    @NonNull
    private Consumer<Object> getConsumer(View v)
    {
        return new Consumer<Object>()
        {
            @Override
            public void accept(Object o)
            {
                goToCalendarActivity(v);
            }
        };
    }

    public void sortGroupCards(View view)
    {
        List<Group> groupList = mAdapter.getGroupList();
        Collections.sort(groupList);
        mAdapter.setGroupList(groupList);
        mAdapter.notifyDataSetChanged();
    }

    public void goToCalendarActivity(View v)
    {
        Intent intent = new Intent(v.getContext(), CalendarActivity.class);
        startActivity(intent);
    }



}
