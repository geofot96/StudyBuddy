package ch.epfl.sweng.studdybuddy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import ch.epfl.sweng.studdybuddy.R;

/**
 * Activity which represents the users profile
 */
public class ProfileTab extends AppCompatActivity {

    /**
     * Set up graphical elements of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);

    }


}
