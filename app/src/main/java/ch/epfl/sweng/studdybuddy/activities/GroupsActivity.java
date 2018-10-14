package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ch.epfl.sweng.studdybuddy.CreateGroup;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.R;

import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;

public class GroupsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Intent other = getIntent();

        RecyclerView rv = (RecyclerView) findViewById(R.id.feedRecycleViewer);//TODO check if it should be removed
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);


        GroupsRecyclerAdapter mAdapter = new GroupsRecyclerAdapter(MainActivity.groupList1);
        rv.setAdapter(mAdapter);

        SearchView sv = (SearchView) findViewById(R.id.feed_search);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

    }

    public void gotoCreation(View view)
    {
        Intent intent = new Intent(this, CreateGroup.class);
        startActivity(intent);
    }

    public void sortGroupCards(View view)
    {
        GroupsRecyclerAdapter mAdapter = new GroupsRecyclerAdapter(MainActivity.groupList1);
        ArrayList<Group> groupList = mAdapter.getGroupList();
        Collections.sort(groupList);
        mAdapter.setGroupList(groupList);
    }
}
