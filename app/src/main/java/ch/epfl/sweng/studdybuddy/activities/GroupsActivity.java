package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.util.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.util.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

public class GroupsActivity extends AppCompatActivity
{
    GroupsRecyclerAdapter mAdapter;
		static List<Group> groupSet  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        RecyclerView rv = (RecyclerView) findViewById(R.id.feedRecycleViewer);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseReference firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
        String userId =  ((StudyBuddy) GroupsActivity.this.getApplication()).getAuthendifiedUser().getUserID().toString();
        Consumer<Intent> buttonClickConsumer = new Consumer<Intent>() {
            @Override
            public void accept(Intent target) { goToCalendarActivity(target); }};
        Intent buttonTransition = new Intent(this, CalendarActivity.class);

        mAdapter = new GroupsRecyclerAdapter(groupSet,userId, buttonClickConsumer);
        rv.setAdapter(mAdapter);
        firebase.select("groups").getAll(Group.class, AdapterConsumer.adapterConsumer(Group.class, groupSet, new RecyclerAdapterAdapter(mAdapter)));
        SearchView sv = (SearchView) findViewById(R.id.feed_search);
        sv.onActionViewExpanded();sv.clearFocus();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query); //FILTER AS YOU TYPE
                return false; }});
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

    public void goToCalendarActivity(Intent target)
    {
        startActivity(target);
    }
}
