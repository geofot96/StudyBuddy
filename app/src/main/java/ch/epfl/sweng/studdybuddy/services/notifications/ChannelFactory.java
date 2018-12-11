package ch.epfl.sweng.studdybuddy.services.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import static android.os.Build.VERSION_CODES.O;

public class ChannelFactory {
    private Version version = new Version();

    @RequiresApi(O)
    public NotificationChannel getNotificationChannel(String id, CharSequence name, int importance) {
        if (version.getBuildVersion() >= O) {
            return new NotificationChannel(id, name, importance);
        }else {
            throw new UnsupportedOperationException();
        }
    }

    public void setVersion(Version version){
        this.version = version;
    }
}
