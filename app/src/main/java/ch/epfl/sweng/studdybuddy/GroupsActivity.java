package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class GroupsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        Intent other = getIntent();
        DummyCourses dummy=new DummyCourses();
        RecyclerView rv=(RecyclerView)findViewById(R.id.testRecycleViewer);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rv.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager lm= new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

        // specify an adapter (see also next example)
        Group g1=new Group(0,5,dummy.getListOfCourses().get(0),new ArrayList<User>());
        Group g2=new Group(3,7,dummy.getListOfCourses().get(4),new ArrayList<User>());
        ArrayList<Group> groupList1=new ArrayList<>();
        groupList1.add(g1);
        groupList1.add(g2);
        GroupsRecyclerAdapter mAdapter = new GroupsRecyclerAdapter(groupList1);
        rv.setAdapter(mAdapter);
        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.testRecycleViewer);


    }

    public void gotoCreation(View view)
    {
        Intent intent = new Intent(this, CreateGroup.class);
        startActivity(intent);
    }
}
