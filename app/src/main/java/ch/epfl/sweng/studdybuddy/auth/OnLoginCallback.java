package ch.epfl.sweng.studdybuddy.auth;

import ch.epfl.sweng.studdybuddy.core.Account;

public interface OnLoginCallback {
    void then(Account acct);
}
