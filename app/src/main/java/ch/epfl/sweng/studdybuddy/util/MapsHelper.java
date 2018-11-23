package ch.epfl.sweng.studdybuddy.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;

public class MapsHelper {
    public static final MeetingLocation ROLEX_LOCATION = new MeetingLocation("Rolex", "EPFL", 46.5182757,6.5660673);
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final float DEFAULT_ZOOM = 14.0f;


    public static MarkerOptions setInitialPosition(@Nullable Meeting meeting){
        MarkerOptions mMarker = new MarkerOptions().position(new LatLng(ROLEX_LOCATION.getLatitude(), ROLEX_LOCATION.getLongitude()));
        //need to select the correct meeting
        if(meeting != null) {
            MeetingLocation location = meeting.getLocation();
            mMarker = (new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(location.getTitle()));
            return ((mMarker.draggable(true)));
        }
        return mMarker;
    }

    public static MeetingLocation mapListener(LatLng latLng ,Marker marker, PlaceAutocompleteFragment autocompleteFragment,  Context ctx){
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

    public static MeetingLocation acceptSelectedPlace(Place place , Marker marker){
        // TODO: Get info about the selected place.
      //  Log.i("Maps", "Place: " + place.getName());
        marker.setPosition(place.getLatLng());
        marker.setTitle(place.getName().toString());
        return(new MeetingLocation(place.getName().toString(), place.getAddress().toString(), place.getLatLng()));
    }
}
