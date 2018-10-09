package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import static ch.epfl.sweng.studdybuddy.DummyCourses.MATHEMATICS;

public class GroupsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_activity);
        Intent other = getIntent();


        DummyCourses dummy=new DummyCourses();
        RecyclerView rv = (RecyclerView) findViewById(R.id.testRecycleViewer);

        rv.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);


        GroupsRecyclerAdapter mAdapter = new GroupsRecyclerAdapter(MainActivity.groupList1);
        rv.setAdapter(mAdapter);

    }

    public void gotoCreation(View view)
    {
        Intent intent = new Intent(this, CreateGroup.class);
        startActivity(intent);
    }
}
