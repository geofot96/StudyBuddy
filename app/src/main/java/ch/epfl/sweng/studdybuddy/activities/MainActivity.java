package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ch.epfl.sweng.studdybuddy.R;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void gotoGroups(View view)
    {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

    public void gotoFred(View view)
    {
        Intent intent = new Intent(this, CourseSelectActivity.class);
        startActivity(intent);
    }


}
