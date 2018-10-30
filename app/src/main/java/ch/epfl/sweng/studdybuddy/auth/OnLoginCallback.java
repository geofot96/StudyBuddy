package ch.epfl.sweng.studdybuddy.auth;

import ch.epfl.sweng.studdybuddy.core.Account;

public interface OnLoginCallback {
    public void then(Account acct);
}
