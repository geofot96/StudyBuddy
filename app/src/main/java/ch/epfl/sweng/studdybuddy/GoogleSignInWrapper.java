package ch.epfl.sweng.studdybuddy;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class GoogleSignInWrapper {
    private boolean onTest;

    public GoogleSignInWrapper(boolean onTest){
        this.onTest = onTest;
    }
    public Task<GoogleSignInAccount> getTask(Intent data){
        if(onTest){
            return null;
        }
        return GoogleSignIn.getSignedInAccountFromIntent(data);
    }
}
