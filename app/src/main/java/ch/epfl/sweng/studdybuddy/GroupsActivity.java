package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GroupsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_activity);
        Intent other = getIntent();
    }

    public void gotoCreation(View view)
    {
        Intent intent = new Intent(this, CreateGroup.class);
        startActivity(intent);
    }
}
