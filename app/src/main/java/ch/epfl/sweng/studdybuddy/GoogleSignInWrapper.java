package ch.epfl.sweng.studdybuddy;

        import android.content.Intent;

        import com.google.android.gms.auth.api.signin.GoogleSignIn;
        import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
        import com.google.android.gms.tasks.Task;

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
