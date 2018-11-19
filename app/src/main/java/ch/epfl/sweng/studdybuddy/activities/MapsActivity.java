package ch.epfl.sweng.studdybuddy.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Meeting;
import ch.epfl.sweng.studdybuddy.core.MeetingLocation;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.Consumer;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MarkerOptions mMarker;
    private Marker marker;
    private List<Meeting> meetings;
    private FirebaseReference ref;
    private MeetingLocation confirmedPlace;
    PlaceAutocompleteFragment autocompleteFragment;

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
        meetings = new ArrayList<>();
        ref = new FirebaseReference();
        setMeetings( ref, meetings);
    }

    //Move camera and
    private PlaceSelectionListener placeSelectionListener(){
        return new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
               MapsHelper.acceptSelectedPlace(place, mMarker, marker, confirmedPlace, mMap);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Maps", "An error occurred: " + status);
            }
        };
    }

    //Write meeting location in firebase
    private View.OnClickListener confirmationListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MapsHelper.confirmationListener(v, confirmedPlace, meetings, ref)){
                    finish();
                }
            }
        };
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
            mMap.setMyLocationEnabled(true);


        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MapsHelper.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MapsHelper.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
               MeetingLocation tmp =  MapsHelper.mapListener(latLng, marker, autocompleteFragment, confirmedPlace, MapsActivity.this);

               if(tmp != null) {
                   confirmedPlace = tmp;
               }
           }
       }
        );
    }


    public  void setMeetings(FirebaseReference ref, final List<Meeting> meetings){
        ref.select("BoubaMeetings").getAll(Meeting.class, new Consumer<List<Meeting>>() {
            @Override
            public void accept(List<Meeting> meetingsfb) {
                Marker tmp =MapsHelper.acceptMeetings(meetingsfb,meetings,marker, mMap);
                if(tmp != null){
                    marker = tmp;
                }
            }
        });
    }



}
