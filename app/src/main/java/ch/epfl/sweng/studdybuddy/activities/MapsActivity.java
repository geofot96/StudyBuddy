package ch.epfl.sweng.studdybuddy.activities;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.MeetingLocation;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.Consumer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private boolean mLocationPermissionGranted = true;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LatLng mDefaultLocation;
    private final float DEFAULT_ZOOM = 14.0f;
    private final String TAG = "MAPS";
    private MarkerOptions mMarker;
    private Marker marker;
    private Button button;
    private List<Meeting> meetings;
    private FirebaseReference ref;
    private MeetingLocation confirmedPlace;
    PlaceAutocompleteFragment autocompleteFragment;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete);

        autocompleteFragment.setOnPlaceSelectedListener(placeSelectionListener());

        ((Button) findViewById(R.id.confirmLocation)).setOnClickListener(confirmationListener());

        setMeetings();

        mGeoDataClient = Places.getGeoDataClient(this);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    private PlaceSelectionListener placeSelectionListener(){
        return new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Maps", "Place: " + place.getName());
                mMarker = new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()).draggable(true);
                marker.setPosition(place.getLatLng());
                marker.setTitle(place.getName().toString());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                confirmedPlace = new MeetingLocation(place.getName().toString(), place.getAddress().toString(), place.getLatLng());

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Maps", "An error occurred: " + status);
            }
        };
    }
    private View.OnClickListener confirmationListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmedPlace != null) {
                    int lastindex = meetings.size() - 1;
                    Meeting lastMeeting = meetings.get(lastindex);
                    lastMeeting.setLocation(confirmedPlace);
                    meetings.set(lastindex, lastMeeting);
                    ref.select("BoubaMeetings").setVal(meetings);
                    finish();
                }
            }
        };
    }

    private void setMeetings(){
        meetings = new ArrayList<>();
        ref = new FirebaseReference();
        ref.select("BoubaMeetings").getAll(Meeting.class, new Consumer<List<Meeting>>() {
            @Override
            public void accept(List<Meeting> meetingsfb) {
                meetings.clear();
                meetings.addAll(meetingsfb);
                MeetingLocation location = meetings.get(meetings.size() - 1).location;
                mDefaultLocation = location.getLatLng();


                mMarker = new MarkerOptions().position(mDefaultLocation).title(location.getTitle());

                marker = mMap.addMarker(mMarker.draggable(true));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            }
        });
    }

    private void getLocationPermission() {
        //
        //Request location permission, so that we can get the location of the
        //device. The result of the permission request is handled by a callback,
        //onRequestPermissionsResult.
        //
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            mMap.setMyLocationEnabled(true);


        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getLocationPermission();

                }
            }
        }
        //updateLocationUI();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.setPosition(latLng);
                Geocoder geocoder;
                Address address = null;
                geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
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
                    confirmedPlace = new MeetingLocation(address.getFeatureName(),address.getAddressLine(0), address.getLatitude(), address.getLongitude());
                }
            }
        });
    }



}
