package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import android.support.v7.widget.SearchView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ch.epfl.sweng.studdybuddy.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.RecyclerAdapterAdapter;


import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;

public class GroupsActivity extends AppCompatActivity
{
    GroupsRecyclerAdapter mAdapter;
		static List<Group> groupSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        RecyclerView rv = (RecyclerView) findViewById(R.id.feedRecycleViewer);//TODO check if it should be removed
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseReference firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
        groupSet = new ArrayList<>();
        mAdapter = new GroupsRecyclerAdapter(groupSet);
        rv.setAdapter(mAdapter);
        firebase.select("groups").getAll(Group.class, AdapterConsumer.adapterConsumer(Group.class, groupSet, new RecyclerAdapterAdapter(mAdapter)));
        SearchView sv = (SearchView) findViewById(R.id.feed_search);
        sv.onActionViewExpanded();
        sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query); //FILTER AS YOU TYPE
                return false;
            }
        });

    }

    public void gotoCreation(View view)
    {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
    }

    public void sortGroupCards(View view)
    {
        List<Group> groupList = mAdapter.getGroupList();
        Collections.sort(groupList);
        mAdapter.setGroupList(groupList);
        mAdapter.notifyDataSetChanged();
    }
}
