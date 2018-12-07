package ch.epfl.sweng.studdybuddy.controllers;

import com.google.firebase.database.ValueEventListener;

import ch.epfl.sweng.studdybuddy.core.Account;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

public final class GoogleSigninController {
    private GoogleSigninController() {}
    public static ValueEventListener fetchUserCallback(ReferenceWrapper fb, Account acct, StudyBuddy app, Intentable destination) {
        return fb.select("users").select(acct.getId()).get(User.class, callbackUserFetch(fb, acct, app, destination));
    }

    public static Consumer<User> callbackUserFetch(ReferenceWrapper fb, Account acct, StudyBuddy app, Intentable destination) {
        return new Consumer<User>() {
            @Override
            public void accept(User user) {
                final ID<User> userID = new ID<>(acct.getId());
                if(user == null) { //create a new user and put in db
                    app.setAuthendifiedUser(new User(acct.getDisplayName(), userID));
                    app.disableTravis();
                    fb.select("users").select(userID.getId()).setVal(app.getAuthendifiedUser());
                }
                else {
                    app.setAuthendifiedUser(user);
                }
                destination.launch();
            }
        };
    }
}
