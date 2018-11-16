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
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.CreateGroupActivity;
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
    static List<Group> groupSet = new ArrayList<>();
    public static final String GROUP_ID = "ch.epfl.sweng.studdybuddy.groupId";
    public static final String IS_PARTICIPANT = "ch.epfl.sweng.studdybuddy.particip";
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

        sortButton = v.findViewById(R.id.sortButton);
        sortButton.setOnClickListener(getOnClickListener());
        actionButton = v.findViewById(R.id.createGroup);
        actionButton.setOnClickListener(getFloatingButtonListener());

        FirebaseReference firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
        String userId = ((StudyBuddy) getActivity().getApplication()).getAuthendifiedUser().getUserID().toString();
        Consumer<Intent> buttonClickConsumer = getButtonClickConsumer();

        mAdapter = new GroupsRecyclerAdapter(groupSet, userId, buttonClickConsumer);
        rv.setAdapter(mAdapter);
        firebase.select("groups").getAll(Group.class, AdapterConsumer.adapterConsumer(Group.class, groupSet, new RecyclerAdapterAdapter(mAdapter)));
        SearchView sv = (SearchView) v.findViewById(R.id.feed_search);
        setUpActivity(rv, sv, v);
        ToggleButton toggleFull = (ToggleButton) v.findViewById(R.id.toggleButton);
        toggleFull.setOnCheckedChangeListener(getToggleListener());


        return v;
    }

    @NonNull
    private Consumer<Intent> getButtonClickConsumer()
    {
        return new Consumer<Intent>()
        {
            @Override
            public void accept(Intent target)
            {
                moveOn(target);
            }
        };
    }

    @NonNull
    private CompoundButton.OnCheckedChangeListener getToggleListener()
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                toggleButtonFullBehaviour(buttonView, isChecked);
            }
        };
    }

    private void setUpActivity(RecyclerView rv, SearchView sv, View v)
    {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setAdapter(mAdapter);
        sv.onActionViewExpanded();
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    public void toggleButtonFullBehaviour(CompoundButton buttonView, boolean isChecked)
    {
        if(isChecked)
        {
            filteredGroupSet.clear();
            selectOnlyAvailableGroups();
            mAdapter.setGroupList(filteredGroupSet);
            mAdapter.setFilterList(filteredGroupSet);
        }
        else
        {
            mAdapter.setGroupList(groupSet);
            mAdapter.setFilterList(groupSet);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void selectOnlyAvailableGroups()
    {
        for(Group g : groupSet)
        {
            if(g.getMaxNoUsers() > mAdapter.getParticipantNumber(g))
                filteredGroupSet.add(g);
        }
    }

    public void goToCalendarActivity(Intent target)
    {
        startActivity(target);
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

    public void sortGroupCards(View view)
    {
        List<Group> groupList = mAdapter.getGroupList();
        Collections.sort(groupList);
        mAdapter.setGroupList(groupList);
        mAdapter.notifyDataSetChanged();
    }

    public void moveOn(Intent intent)
    {
        startActivity(intent);
    }


}
