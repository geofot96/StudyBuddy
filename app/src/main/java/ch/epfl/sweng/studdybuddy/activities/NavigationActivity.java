package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ch.epfl.sweng.studdybuddy.Fragments.ChatFragment;
import ch.epfl.sweng.studdybuddy.Fragments.FeedFragment;
import ch.epfl.sweng.studdybuddy.Fragments.ProfileFragment;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.auth.AuthManager;
import ch.epfl.sweng.studdybuddy.auth.FirebaseAuthManager;
import ch.epfl.sweng.studdybuddy.auth.GoogleSignInActivity;
import ch.epfl.sweng.studdybuddy.core.Account;

public class NavigationActivity extends AppCompatActivity
{

    private AuthManager mAuth = null;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private ChatFragment chatFragment;
    private FeedFragment feedFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        chatFragment = new ChatFragment();
        feedFragment = new FeedFragment();
        profileFragment = new ProfileFragment();
        setFragment(feedFragment);
        mMainNav.setOnNavigationItemSelectedListener(getListener());
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Account currentUser = getAuthManager().getCurrentUser();
        if(currentUser == null)
        {
            signOut();
        }
        else
        {

        }

    }

    private void signOut()
    {
        getAuthManager().logout().addOnCompleteListener(this,
                new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        startActivity(new Intent(NavigationActivity.this, GoogleSignInActivity.class));
                    }
                });
    }

    public AuthManager getAuthManager()
    {
        if(mAuth == null)
        {
            mAuth = new FirebaseAuthManager(this, getString(R.string.default_web_client_id));
        }
        return mAuth;
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
                    case R.id.navToCalendar:
                        setFragment(chatFragment);
                        return true;
                    case R.id.navToHome:
                        setFragment(feedFragment);
                        return true;
                    case R.id.navToProfile:
                        setFragment(profileFragment);
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
