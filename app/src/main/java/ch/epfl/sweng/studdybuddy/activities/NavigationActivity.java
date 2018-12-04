package ch.epfl.sweng.studdybuddy.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.List;

import ch.epfl.sweng.studdybuddy.Fragments.ChatListsFragment;
import ch.epfl.sweng.studdybuddy.Fragments.FeedFragment;
import ch.epfl.sweng.studdybuddy.Fragments.ProfileFragment;
import ch.epfl.sweng.studdybuddy.Fragments.SettingsFragment;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.sql.DAOs.UserDAO;
import ch.epfl.sweng.studdybuddy.sql.SqlDB;

public class NavigationActivity extends AppCompatActivity
{
    //TODO move the signout button from the main activity somewhere else and transfer the logic there

    private ChatListsFragment chatFragment;
    private FeedFragment feedFragment;
    private ProfileFragment profileFragment;
    private SettingsFragment settingsFragment;
    private static final String DATABASE_NAME = "StudyBuddy";
    private SqlDB sql;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        FrameLayout mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        BottomNavigationView mMainNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        chatFragment = new ChatListsFragment();
        feedFragment = new FeedFragment();
        profileFragment = new ProfileFragment();
        settingsFragment = new SettingsFragment();
        setFragment(feedFragment);
        mMainNav.setOnNavigationItemSelectedListener(getListener());



        sql = Room.inMemoryDatabaseBuilder(this, SqlDB.class).build();

        UserDAO userdao = sql.userDAO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                userdao.insert( new User("test", "goku"));
                List<User> users =  userdao.getAll();
                for(User u : users){
                    Log.d("D",u.getName());
                }

            }
        }).start();

    }








    @NonNull
    private BottomNavigationView.OnNavigationItemSelectedListener getListener()
    {
        return new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch(menuItem.getItemId())
                {
                    case R.id.navToChat:
                        setFragment(chatFragment);
                        return true;
                    case R.id.navToHome:
                        setFragment(feedFragment);
                        return true;
                    case R.id.navToProfile:
                        setFragment(profileFragment);
                        return true;
                    case R.id.navToSettings:
                        setFragment(settingsFragment);
                        return true;


                    default:
                        return false;
                }
            }
        };
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
