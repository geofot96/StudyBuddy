package ch.epfl.sweng.studdybuddy;

import android.app.Application;

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