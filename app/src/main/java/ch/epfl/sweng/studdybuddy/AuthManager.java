package ch.epfl.sweng.studdybuddy;

import com.google.android.gms.tasks.Task;

public interface AuthManager {
    void login(Account acct, OnLoginCallback f, String TAG);
    Task<Void> logout();
    void startLoginScreen();
    Account getCurrentUser();
}
