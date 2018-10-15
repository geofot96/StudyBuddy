package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.R;

import static ch.epfl.sweng.studdybuddy.ReferenceWrapper.adapterConsumer;

public class GroupsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Intent other = getIntent();

        RecyclerView rv = (RecyclerView) findViewById(R.id.testRecycleViewer);
        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseReference firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
        List<Group> groupSet = new ArrayList<>();
        GroupsRecyclerAdapter mAdapter = new GroupsRecyclerAdapter(groupSet);
        rv.setAdapter(mAdapter);

        firebase.select("groups").getAll(Group.class, adapterConsumer(Group.class, groupSet, mAdapter));
    }

    public void gotoCreation(View view)
    {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
    }

}
