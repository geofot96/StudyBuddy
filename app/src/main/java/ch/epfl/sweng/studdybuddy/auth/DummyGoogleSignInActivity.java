package ch.epfl.sweng.studdybuddy.auth;

import com.google.android.gms.tasks.Task;

import ch.epfl.sweng.studdybuddy.core.Account;

public class DummyGoogleSignInActivity extends GoogleSignInActivity {

    @Override
    public AuthManager getAuthManager(){
        return new AuthManager() {
            @Override
            public void login(Account acct, OnLoginCallback f, String TAG) {
                f.then(acct);
            }

            @Override
            public Task<Void> logout() {
                return null;
            }


            @Override
            public void startLoginScreen() {
            }

            @Override
            public Account getCurrentUser() {
                return null;
            }

        };
    }

    @Override
    public boolean onTest(){
        return true;
    }

}
