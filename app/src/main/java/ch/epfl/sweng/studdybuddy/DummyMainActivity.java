package ch.epfl.sweng.studdybuddy;

import com.google.android.gms.tasks.Task;

public class DummyMainActivity extends MainActivity{

    @Override
    public AuthManager getAuthManager(){
        return new AuthManager() {
            @Override
            public void login(Account acct, OnLoginCallback f, String TAG) {

            }

            @Override
            public Task<Void> logout() {
                return new EmptyTask();
            }

            @Override
            public void startLoginScreen() {

            }

            @Override
            public Account getCurrentUser() {
                    return new Account();
            }
        };
    }
}
