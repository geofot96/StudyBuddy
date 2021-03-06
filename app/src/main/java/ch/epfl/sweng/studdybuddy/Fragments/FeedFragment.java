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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.CreateGroupActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.tools.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

/**
 * A fragment containing the feed of available groups
 */
public class FeedFragment extends Fragment
{
    GroupsRecyclerAdapter mAdapter;
    static List<Group> groupSet = new ArrayList<>();
    private static List<String> userCourses = new ArrayList<>();
    public static final String IS_PARTICIPANT = "ch.epfl.sweng.studybuddy.particip";
    static Set<Group> filteredGroupSetFull = new HashSet<>();
    static Set<Group> filteredGroupSetUserCourses = new HashSet<>();
    private Button sortButton;
    private FloatingActionButton actionButton;
    private ToggleButton toggleFull, toggleUserCourses;

    /**
     * Required empty public constructor
     */
    public FeedFragment() {

    }

    /**
     * Sets up the graphical elements of the Fragment
     * @param inflater Inflater containing the list of existing chats
     * @param container container of the list
     * @param savedInstanceState previously saved state
     * @return The inflatet view containing the list of chats
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        firebase.select(Messages.FirebaseNode.GROUPS).getAll(Group.class, AdapterConsumer.adapterConsumer(Group.class, groupSet, new RecyclerAdapterAdapter(mAdapter)));
        SearchView sv = (SearchView) v.findViewById(R.id.feed_search);
        setUpActivity(rv, sv, v);

        toggleFull = (ToggleButton) v.findViewById(R.id.toggleAllCourses);
        toggleUserCourses = (ToggleButton) v.findViewById(R.id.toggleYourCourses);
        toggleFull.setOnCheckedChangeListener(getToggleListener());
        toggleUserCourses.setOnCheckedChangeListener(getToggleListener());
        MetaGroup metabase = new MetaGroup();
        metabase.getUserCourses(((StudyBuddy) getActivity().getApplication()).getAuthendifiedUser().getUserID().toString(), userCourses);
        System.out.print(userCourses);


        return v;
    }

    /**
     * Returns a listener that starts the given activity
     * @return A consumer that does the given action
     */
    @NonNull
    private Consumer<Intent> getButtonClickConsumer() {
        return new Consumer<Intent>() {
            @Override
            public void accept(Intent target) {
                moveOn(target);
            }
        };
    }

    /**
     * Listener for the toggle buttons that decides what behaviour to do
     * @return a listener that does the aforementioned functionality
     */
    @NonNull
    private CompoundButton.OnCheckedChangeListener getToggleListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleButtonBehaviour(buttonView, isChecked);
            }
        };
    }

    /**
     * Set up the graphical components of the view
     * @param rv The recyclerViewer containing the different classes
     * @param sv The SearchView where te user types to filter
     * @param v the current view
     */
    private void setUpActivity(RecyclerView rv, SearchView sv, View v) {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
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

    /**
     * Filters the available groups depending on which toggle are selected
     * @param buttonView the buttonview that has changed
     * @param isChecked boolean describing the state of the toggle
     */
    public void toggleButtonBehaviour(CompoundButton buttonView, boolean isChecked) {
        Set filteredGroupSet = new HashSet(groupSet);
        ;
        if (toggleFull.isChecked()) {
            selectOnlyAvailableGroups();

            filteredGroupSet = new HashSet(filteredGroupSetFull);
            if (toggleUserCourses.isChecked()) {
                selectOnlyGroupsWithUserCourses();
                filteredGroupSet.retainAll(filteredGroupSetUserCourses);
                //filteredGroupSetFull.clear();
                selectOnlyAvailableGroups();

            }
        } else if (toggleUserCourses.isChecked()) {
            selectOnlyGroupsWithUserCourses();
            filteredGroupSet = new HashSet(filteredGroupSetUserCourses);
        }
        mAdapter.setGroupList(new ArrayList<>(filteredGroupSet));
        mAdapter.setFilterList(new ArrayList<>(filteredGroupSet));
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Keeps only the groups with free spaces for more participants
     */
    private void selectOnlyAvailableGroups() {
        filteredGroupSetFull.clear();
        for (Group g : groupSet) {
            if (g.getMaxNoUsers() > mAdapter.getParticipantNumber(g))
                filteredGroupSetFull.add(g);
        }
    }

    /**
     * Keeps only the groups with the same courses as the user's preferred ones
     */
    private void selectOnlyGroupsWithUserCourses() {
        filteredGroupSetUserCourses.clear();
        for (Group g : groupSet) {
            if (userCourses.contains(g.getCourse().getCourseName()))
                filteredGroupSetUserCourses.add(g);
        }
    }

    /**
     * Listener that starts an activity changing the current view to the CreateGroupActivity
     * @return the aforementioned listener
     */
    @NonNull
    private View.OnClickListener getFloatingButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateGroupActivity.class);
                startActivity(intent);
            }
        };
    }

    /**
     * Listener which triggers the sorting by date of the list of groups
     * @return the aforementioned listener
     */
    @NonNull
    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGroupCards(v);
            }
        };
    }

    /**
     * Sorts the current list of groups according to creation date
     * @param view the current view
     */
    public void sortGroupCards(View view) {
        List<Group> groupList = mAdapter.getGroupList();
        Collections.sort(groupList);
        mAdapter.setGroupList(groupList);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Start the given intent
     * @param intent intent to be started
     */
    public void moveOn(Intent intent) {
        startActivity(intent);
    }


}