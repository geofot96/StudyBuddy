package ch.epfl.sweng.studdybuddy.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
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
    private static final float DEFAULT_ZOOM = 14.0f;


    public static Marker acceptMeetings(List<Meeting>meetingsFb, List<Meeting> meetings, Marker marker, GoogleMap mMap){
        boolean isActivityInitialized = meetings.size() > 0;
        meetings.clear();
        meetings.addAll(meetingsFb);
        //need to select the correct meeting
        if(!isActivityInitialized) {
            MeetingLocation location = meetings.get(meetings.size() - 1).location;
            MarkerOptions mMarker =(new MarkerOptions().position(location.getLatLng()).title(location.getTitle()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location.getLatLng(), DEFAULT_ZOOM));
            return (mMap.addMarker(mMarker.draggable(true)));


        }
        return null;
    }

    public static boolean confirmationListener(View v, MeetingLocation confirmedPlace, List<Meeting> meetings, FirebaseReference ref){
                if (confirmedPlace != null) {
                    int lastindex = meetings.size() - 1;
                    Meeting lastMeeting = meetings.get(lastindex);
                    lastMeeting.setLocation(confirmedPlace);
                    meetings.set(lastindex, lastMeeting);
                    ref.select("BoubaMeetings").setVal(meetings);
                    return true;
                }
                return false;

    };

    public static MeetingLocation mapListener(LatLng latLng ,Marker marker, PlaceAutocompleteFragment autocompleteFragment, MeetingLocation confirmedPlace, Context ctx){
        marker.setPosition(latLng);
        Geocoder geocoder;
        Address address = null;
        geocoder = new Geocoder(ctx, Locale.getDefault());
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
            return  (new  MeetingLocation(address.getFeatureName(),address.getAddressLine(0), address.getLatitude(), address.getLongitude()));
        }
        return null;
    }

    public static void acceptSelectedPlace(Place place , MarkerOptions mMarker, Marker marker, MeetingLocation confirmedPlace, GoogleMap mMap){
        // TODO: Get info about the selected place.
        Log.i("Maps", "Place: " + place.getName());
        mMarker = new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()).draggable(true);
        marker.setPosition(place.getLatLng());
        marker.setTitle(place.getName().toString());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
        confirmedPlace = (new MeetingLocation(place.getName().toString(), place.getAddress().toString(), place.getLatLng()));
    }
}

