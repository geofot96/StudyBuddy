package ch.epfl.sweng.studdybuddy.util;

import android.app.Application;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;

public class StudyBuddy extends Application {
    public User getAuthendifiedUser() {
        if(authendifiedUser == null) {
            return new User("Default", new ID<>("Default"));
        }else {
            return authendifiedUser;
        }
    }

    public void setAuthendifiedUser(User authendifiedUser) {
        this.authendifiedUser = authendifiedUser;
    }

    public User authendifiedUser;
}