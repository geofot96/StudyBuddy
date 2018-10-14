package ch.epfl.sweng.studdybuddy;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public class GoogleSignInWrapper {
    public static Task<GoogleSignInAccount> getTask(Intent data){
        return GoogleSignIn.getSignedInAccountFromIntent(data);
    }
}
