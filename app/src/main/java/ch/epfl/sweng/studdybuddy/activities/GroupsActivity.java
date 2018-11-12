package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;

import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.sweng.studdybuddy.util.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.util.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.util.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.activities.CreateGroupActivity;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

public class GroupsActivity extends AppCompatActivity {
    GroupsRecyclerAdapter mAdapter;
    static List<Group> groupSet = new ArrayList<>();
    static List<Group> filteredGroupSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        RecyclerView rv = (RecyclerView) findViewById(R.id.feedRecycleViewer);

        FirebaseReference firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
        String userId = ((StudyBuddy) GroupsActivity.this.getApplication()).getAuthendifiedUser().getUserID().toString();
        Consumer<Object> consumer = new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                goToCalendarActivity();
            }
        };
        mAdapter = new GroupsRecyclerAdapter(groupSet, userId, consumer);
        firebase.select("groups").getAll(Group.class, AdapterConsumer.adapterConsumer(Group.class, groupSet, new RecyclerAdapterAdapter(mAdapter)));
        SearchView sv = (SearchView) findViewById(R.id.feed_search);
        setUpActivity(rv, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query); //FILTER AS YOU TYPE
                return false;
            }
        });
        ToggleButton toggleFull = (ToggleButton) findViewById(R.id.toggleButton);
        toggleFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { toggleButtonFullBehaviour(buttonView,isChecked);}});
    }

    private void setUpActivity(RecyclerView rv, SearchView sv) {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
        sv.onActionViewExpanded();
        sv.clearFocus();
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

    public void gotoCreation(View view) {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
    }

    public void sortGroupCards(View view) {
        List<Group> groupList = mAdapter.getGroupList();
        Collections.sort(groupList);
        mAdapter.setGroupList(groupList);
        mAdapter.notifyDataSetChanged();
    }



    public void goToCalendarActivity() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

}
