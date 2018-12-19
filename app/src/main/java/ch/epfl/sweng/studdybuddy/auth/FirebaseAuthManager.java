package ch.epfl.sweng.studdybuddy.auth;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import ch.epfl.sweng.studdybuddy.core.Account;

public class FirebaseAuthManager implements AuthManager {
    private FirebaseAuth mAuth;
    private GoogleSignInClient client;
    private Activity ctx;

    public FirebaseAuthManager(Activity currentActivity, String clientID){
        this.ctx = currentActivity;
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();

        this.client = GoogleSignIn.getClient(currentActivity, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuthManager(FirebaseAuth mAuth, GoogleSignInClient client, Activity ctx) {
        this.mAuth = mAuth;
        this.client = client;
        this.ctx = ctx;
    }

    public void login(Account acct, OnLoginCallback f, String TAG){
        OnCompleteListener ocl = new OnCompleteListenerWrapper<AuthResult>(acct, f, TAG, "SignInWithCredentials");
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(ctx, ocl);
    }



    public void startLoginScreen(){
        int RC_SIGN_IN = 1;
        Intent signInIntent = client.getSignInIntent();
        ctx.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public Task<Void> logout(){
        mAuth.signOut();
        // Google sign out
        return client.signOut();
    }

    public Account getCurrentUser() {
        System.out.println("return firebase user");
        return Account.from(mAuth.getCurrentUser());
    }
}
