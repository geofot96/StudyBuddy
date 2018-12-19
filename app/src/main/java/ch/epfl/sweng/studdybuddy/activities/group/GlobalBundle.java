package ch.epfl.sweng.studdybuddy.activities.group;

import android.os.Bundle;
import android.support.annotation.Nullable;

import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class GlobalBundle {
    private static GlobalBundle Instance = null;

    private final Bundle savedBundle = new Bundle();

    private static boolean meetingExist = false;

    private GlobalBundle() {
    }


    public static GlobalBundle getInstance() {
        synchronized (GlobalBundle.class) {
            if (Instance == null) {
                Instance = new GlobalBundle();
            }
            return Instance;
        }
    }

    public void putAll(@Nullable Bundle bundle) {
        if (bundle != null) {
            savedBundle.putAll(bundle);
        }
    }

    public Bundle getSavedBundle() {
        return savedBundle;
    }

    public void putMeeting(Meeting meeting) {
        if (meeting != null) {
            savedBundle.putString(Messages.meetingID, meeting.getId().getId());
            savedBundle.putLong(Messages.M_SDATE, meeting.getStarting());
            savedBundle.putLong(Messages.M_EDATE, meeting.getEnding());
            MeetingLocation meetingLocation = meeting.getLocation();
            savedBundle.putString(Messages.LOCATION_TITLE, meetingLocation.getTitle());
            savedBundle.putString(Messages.ADDRESS, meetingLocation.getAddress());
            savedBundle.putDouble(Messages.LATITUDE, meetingLocation.getLatitude());
            savedBundle.putDouble(Messages.LONGITUDE, meetingLocation.getLongitude());
            meetingExist = true;
        }
    }

    public Meeting getMeeting() {
        if (meetingExist) {
            MeetingLocation meetingLocation = new MeetingLocation(savedBundle.getString(Messages.LOCATION_TITLE), savedBundle.getString(Messages.ADDRESS), savedBundle.getDouble(Messages.LATITUDE), savedBundle.getDouble(Messages.LONGITUDE));
            Meeting meeting = new Meeting(savedBundle.getLong(Messages.M_SDATE), savedBundle.getLong(Messages.M_EDATE), meetingLocation, savedBundle.getString(Messages.meetingID));
            return meeting;
        }
        return null;
    }

    public void clear(){
        savedBundle.clear();
        meetingExist = false;
    }
}