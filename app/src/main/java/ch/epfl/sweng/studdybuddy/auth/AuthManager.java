package ch.epfl.sweng.studdybuddy.auth;

import com.google.android.gms.tasks.Task;

import ch.epfl.sweng.studdybuddy.core.Account;

public interface AuthManager {
    void login(Account acct, OnLoginCallback f, String TAG);
    Task<Void> logout();
    void startLoginScreen();
    Account getCurrentUser();
}
