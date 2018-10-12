package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ch.epfl.sweng.studdybuddy.R;

public class ProfileTab extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "ch.epfl.sweng.studdybuddy.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);
    }

    public void openCoursesActivity(View view) {
        Intent intent = new Intent(this, ProfileCourses.class);
        startActivity(intent);
    }
    public void openGroupsActivity(View view) {
        Intent intent = new Intent(this, ProfileGroups.class);
        startActivity(intent);
    }
}
