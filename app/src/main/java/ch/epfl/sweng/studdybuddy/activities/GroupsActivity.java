package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.CreateGroup;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.FirebaseWrapper;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.R;

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
        FirebaseWrapper firebase = new FirebaseWrapper(new FirebaseReference());


        GroupsRecyclerAdapter mAdapter = new GroupsRecyclerAdapter(firebase.getAllGroups());
        rv.setAdapter(mAdapter);

    }

    public void gotoCreation(View view)
    {
        Intent intent = new Intent(this, CreateGroup.class);
        startActivity(intent);
    }
}
