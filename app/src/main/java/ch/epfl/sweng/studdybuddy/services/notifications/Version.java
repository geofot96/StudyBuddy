package ch.epfl.sweng.studdybuddy.services.notifications;

import android.os.Build;

public class Version {
    public int getBuildVersion(){
        return Build.VERSION.SDK_INT;
    }
}
