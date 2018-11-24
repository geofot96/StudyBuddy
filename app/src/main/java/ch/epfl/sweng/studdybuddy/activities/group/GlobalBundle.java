package ch.epfl.sweng.studdybuddy.activities.group;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class GlobalBundle {
    private static GlobalBundle Instance = null;

    private final Bundle savedBundle = new Bundle();


    private GlobalBundle(){}


    public static GlobalBundle getInstance(){
        synchronized (GlobalBundle.class){
            if(Instance == null){
                Instance = new GlobalBundle();
            }
            GlobalBundle.class.notifyAll();
            return Instance;
        }
    }

    public void putAll(@Nullable Bundle bundle){
        if(bundle != null) {
            savedBundle.putAll(bundle);
        }
    }

    public Bundle getSavedBundle() {
        return savedBundle;
    }
}
