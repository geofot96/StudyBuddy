package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ch.epfl.sweng.studdybuddy.R;

public class MainActivity extends AppCompatActivity
{

    //TODO move the signout button from the main activity somewhere else and transfer the logic there

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void goToProfileTabred(View view)
    {
        Intent intent = new Intent(this, ProfileTab.class);
        startActivity(intent);
    }

    public void gotoGroups(View view)
    {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

}