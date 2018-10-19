package ch.epfl.sweng.studdybuddy;

import android.app.Application;

public class StudyBuddy extends Application {
    public User getAuthendifiedUser() {
        return authendifiedUser;
    }

    public void setAuthendifiedUser(User authendifiedUser) {
        this.authendifiedUser = authendifiedUser;
    }

    public User authendifiedUser;
}
