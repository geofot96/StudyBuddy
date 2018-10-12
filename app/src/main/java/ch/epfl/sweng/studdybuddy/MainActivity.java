package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity
{
    private AuthManager mAuth = new FirebaseAuthManager(this, getString(R.string.default_web_client_id));;

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
        Account currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            signOut();
        }
    }


    private void signOut(){
        mAuth.logout().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
                    }
                });
    }

    public AuthManager getmAuth(){
        return this.mAuth;
    }

    public void setmAuth(AuthManager mAuth){
        this.mAuth = mAuth;
    }
}

