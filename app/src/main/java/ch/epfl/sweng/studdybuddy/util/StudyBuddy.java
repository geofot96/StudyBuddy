package ch.epfl.sweng.studdybuddy.util;

import android.app.Application;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;

public class StudyBuddy extends Application {
    public User authendifiedUser;
    private boolean onTravis = true;
    public User getAuthendifiedUser() {
        if(authendifiedUser == null) {
            authendifiedUser =  new User("Default", new ID<>("Default"));
            return authendifiedUser;
        }else {
            return authendifiedUser;
        }
    }

    public synchronized void setAuthendifiedUser(User authendifiedUser) {
        this.authendifiedUser = authendifiedUser;
    }

    public void disableTravis(){
        onTravis =false;
    }

    public boolean isOnTravis(){
        return onTravis;
    }

}