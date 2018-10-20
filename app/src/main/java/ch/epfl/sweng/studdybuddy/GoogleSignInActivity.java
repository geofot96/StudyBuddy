package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import ch.epfl.sweng.studdybuddy.activities.*;

public class GoogleSignInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "GoogleActivity";

    private AuthManager mAuth = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

        SignInButton mGoogleBtn = findViewById(R.id.googleBtn);
        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTest()){
                    onActivityResult(1,0,null);
                }else{
                    getAuthManager().startLoginScreen();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        Account acct = getAuthManager().getCurrentUser();
        if (acct != null ) {
            String personName = acct.getDisplayName();
            //appears only when the user is connected
            Toast.makeText(this, "Welcome " + personName, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(GoogleSignInActivity.this, MainActivity.class));
        } else {
            //appears only when the user isn't connected to the app
            Toast.makeText(this, "No User", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInWrapper gsw = new GoogleSignInWrapper(onTest());
            Task<GoogleSignInAccount> task = gsw.getTask(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                Account account = getRightAccount(task);
                getAuthManager().login(account, new OnLoginCallback() {
                    @Override
                    public void then(Account acct) {
                        if (acct != null) {
                            fetchUserAndStart(acct);
                        }
                    }
                }, TAG);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    void fetchUserAndStart(Account acct) {
        FirebaseReference fb = new FirebaseReference();
        fb.select("users").get(User.class, new Consumer<User>() {
            @Override
            public void accept(User user) {
                StudyBuddy app = ((StudyBuddy) GoogleSignInActivity.this.getApplication());
                if(user == null) { //create a new user and put in db
                    ID<User> userID = new ID<>(acct.getId());
                    app.setAuthendifiedUser(new User(acct.getDisplayName(), userID, new ArrayList<>()));
                    fb.select("users").select(userID.getId()).setVal(app.getAuthendifiedUser());
                }
                else {
                    app.setAuthendifiedUser(user);
                }
                startActivity(new Intent(GoogleSignInActivity.this, CourseSelectActivity.class));
            }
        });
    }


    AuthManager getAuthManager(){
        if (mAuth == null){
            mAuth = new FirebaseAuthManager(this, getString(R.string.default_web_client_id));
        }
        return mAuth;
    }

    boolean onTest(){
        return false;
    }

    private Account getRightAccount(Task<GoogleSignInAccount> task) throws ApiException {
        return onTest() ? new Account() : Account.from(task.getResult(ApiException.class));
    }
}