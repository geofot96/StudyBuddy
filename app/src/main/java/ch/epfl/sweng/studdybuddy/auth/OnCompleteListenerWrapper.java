package ch.epfl.sweng.studdybuddy.auth;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ch.epfl.sweng.studdybuddy.auth.OnLoginCallback;
import ch.epfl.sweng.studdybuddy.core.Account;


public class OnCompleteListenerWrapper<TResult> implements OnCompleteListener<TResult> {
    private Account acct;
    private OnLoginCallback f;
    private String TAG;
    private String action;

    public OnCompleteListenerWrapper(Account acct, OnLoginCallback f, String TAG, String action){
        this.acct = acct;
        this.f = f;
        this.TAG = TAG;
        this.action = action;

    }
    public void onComplete(Task<TResult> task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, action+": success");
            f.then(acct);
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG,action+": failure", task.getException());
            f.then(acct);
        }
    }
}
