package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.auth.AuthManager;
import ch.epfl.sweng.studdybuddy.auth.FirebaseAuthManager;
import ch.epfl.sweng.studdybuddy.auth.GoogleSignInActivity;
import ch.epfl.sweng.studdybuddy.core.Account;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class MainActivity extends AppCompatActivity
{
    private AuthManager mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mSignOutBtn = findViewById(R.id.signout_btn);

        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(); //get signed out
            }
        });
    }



    @Override
    protected void onStart(){
        super.onStart();
        Account currentUser = getAuthManager().getCurrentUser();
        if(currentUser == null){
            signOut();
        }

    }


    private void signOut(){
        getAuthManager().logout().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
                    }
                });
    }

    public AuthManager getAuthManager(){
        if (mAuth == null){
            mAuth = new FirebaseAuthManager(this, getString(R.string.default_web_client_id));
        }
        return mAuth;
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

    public void goToCalendarActivity(View view)
    {
        String dummyGroupID = "3f99660b-79d0-42d2-aecb-e7eedbafcb41";
        String dummyUserID = "ZqHa4GLFGEfbuQW9gO6E0kndWNw2";
        Integer dummyInt = new Integer(6);
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra(Messages.groupID, dummyGroupID);
        intent.putExtra(Messages.userID, dummyUserID);
        intent.putExtra(Messages.maxUser, dummyInt);
        startActivity(intent);
    }
    public void goToMergedCalendarActivity(View view)
    {
        Intent intent = new Intent(this, MergedCalendarActivity.class);
        startActivity(intent);
    }


}
