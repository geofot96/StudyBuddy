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
            OnLoginCallback loginCallback = new OnLoginCallback() {
                @Override
                public void then(Account acct) {
                    if (acct != null) {
                        String personName = acct.getDisplayName();
                        //appears only when the user is connected
                        //Toast.makeText(this, "Welcome" + personName, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GoogleSignInActivity.this, MainActivity.class));
                    }/* else {
                            //appears only when the user isn't connected to the app
                            Toast.makeText(this, "No User", Toast.LENGTH_SHORT).show();
                        }*/
                }
            };

            if(!onTest()){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount acct = task.getResult(ApiException.class);
                    Account account = Account.from(acct);
                    getAuthManager().login(account, loginCallback, TAG);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);

                }
            } else {
                getAuthManager().login(new Account(), loginCallback, TAG);
            }

        }
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

}
