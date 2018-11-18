package ch.epfl.sweng.studdybuddy.util;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ch.epfl.sweng.studdybuddy.activities.MapsActivity;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.MeetingLocation;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;

public class MapsHelper {
    MapsActivity activity;
    public static final MeetingLocation ROLEX_LOCATION = new MeetingLocation("Rolex", "EPFL", 46.5182757,6.5660673);
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    public MapsHelper(MapsActivity activity){
        activity = activity;
    }

    public static View.OnClickListener confirmationListener(MeetingLocation confirmedPlace, List<Meeting> meetings, FirebaseReference ref, Activity activity){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmedPlace != null) {
                    int lastindex = meetings.size() - 1;
                    Meeting lastMeeting = meetings.get(lastindex);
                    lastMeeting.setLocation(confirmedPlace);
                    meetings.set(lastindex, lastMeeting);
                    ref.select("BoubaMeetings").setVal(meetings);
                    activity.finish();
                }
            }
        };
     }

     public static GoogleMap.OnMapClickListener getMapClickListener(MapsActivity activity, Marker marker, PlaceAutocompleteFragment autocompleteFragment) {
       return new GoogleMap.OnMapClickListener() {

        @Override
        public void onMapClick(LatLng latLng) {
            marker.setPosition(latLng);
            Geocoder geocoder;
            Address address = null;
            geocoder = new Geocoder(activity, Locale.getDefault());
            try {
                address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (address != null)
            {
                marker.setTitle(address.getFeatureName() + " " + address.getAddressLine(0));
                marker.showInfoWindow();
                autocompleteFragment.setText(address.getFeatureName() + " " + address.getAddressLine(0));
                activity.setConfirmedPlace(new  MeetingLocation(address.getFeatureName(),address.getAddressLine(0), address.getLatitude(), address.getLongitude()));
            }
        }};
    }

    public static void setMeetings(MapsActivity activity, FirebaseReference ref, final List<Meeting> meetings){
        ref = new FirebaseReference();
        ref.select("BoubaMeetings").getAll(Meeting.class, new Consumer<List<Meeting>>() {
            @Override
            public void accept(List<Meeting> meetingsfb) {
                boolean isActivityInitialized = meetings.size() > 0;
                meetings.clear();
                meetings.addAll(meetingsfb);
                //need to select the correct meeting
                if(!isActivityInitialized) {
                    MeetingLocation location = meetings.get(meetings.size() - 1).location;
                    activity.setMarkerOption(new MarkerOptions().position(location.getLatLng()).title(location.getTitle()));
                    activity.initMarker();
                    activity.moveCamera(location);
                }
            }
        });
    }}

