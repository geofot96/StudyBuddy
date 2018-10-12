package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
}
