package ch.epfl.sweng.studdybuddy.activities.group;

import android.content.Intent;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private MarkerOptions mMarker;  //TODO remove it if never used
    private Marker marker;
    private MeetingLocation confirmedPlace;
    private Bundle origin;

    private PlaceAutocompleteFragment autocompleteFragment;
    private User user;
    private String uId;
    private String gId;
    private String adminID;
    private String meetingID;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete);
        user = ((StudyBuddy) MapsActivity.this.getApplication()).getAuthendifiedUser();
        uId = user.getUserID().getId();
        origin = GlobalBundle.getInstance().getSavedBundle();
        gId = origin.getString(Messages.groupID);
        adminID = origin.getString(Messages.ADMIN);
        meetingID = origin.getString(Messages.meetingID);
        try{
            mapFragment.getMapAsync(this);
        }catch(NullPointerException e){
            startActivity(new Intent(this, NavigationActivity.class));
            e.printStackTrace();
        }

    }

    //Move camera and
    private PlaceSelectionListener placeSelectionListener(){
        return new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                confirmedPlace = MapsHelper.acceptSelectedPlace(place, marker);
                mMarker = new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()).draggable(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));

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
                if(confirmedPlace != null) {
                    Intent resultIntent = new Intent();
                    origin.putString(Messages.LOCATION_TITLE, confirmedPlace.getTitle());
                    origin.putString(Messages.ADDRESS, confirmedPlace.getAddress());
                    origin.putDouble(Messages.LATITUDE, confirmedPlace.getLatitude());
                    origin.putString(Messages.meetingID, meetingID);
                    origin.putDouble(Messages.LONGITUDE, confirmedPlace.getLongitude());
                    GlobalBundle.getInstance().putAll(origin);
                    /*resultIntent.putExtras(origin);*/
                    setResult(RESULT_OK, resultIntent);
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
        autocompleteFragment.setOnPlaceSelectedListener(placeSelectionListener());
        button = findViewById(R.id.confirmLocation);
        button.setOnClickListener((confirmationListener()));
        FirebaseReference ref = new FirebaseReference();
        initialization(ref);
        if(adminID.equals(uId) || gId.equals(Messages.settingsPlaceHolder)){
            button.setVisibility(View.VISIBLE);
            button.setEnabled(true);
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

               @Override
               public void onMapClick(LatLng latLng) {
                   MeetingLocation tmp =  MapsHelper.mapListener(latLng, marker, autocompleteFragment, MapsActivity.this);

                   if(tmp != null) {
                       confirmedPlace = tmp;
                   }
               }
           }
        );
    }


    private void initialization(FirebaseReference ref){
        if(gId.equals(Messages.settingsPlaceHolder)){
            MeetingLocation location = user.getFavoriteLocation();
            if(location == null){
                location = MapsHelper.ROLEX_LOCATION;
            }
            mMarker = (new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(location.getTitle()));
            mMarker.draggable(true);
            marker = mMap.addMarker(mMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), MapsHelper.DEFAULT_ZOOM));

        }else {
            ref.select(Messages.FirebaseNode.MEETINGS).select(gId).select(meetingID).get(Meeting.class, new Consumer<Meeting>() {
                @Override
                public void accept(Meeting meeting) {
                    MarkerOptions tmp = MapsHelper.setInitialPosition(meeting);

                    if (tmp != null) {
                        marker = mMap.addMarker(tmp);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), MapsHelper.DEFAULT_ZOOM));

                    }
                }
            });
        }

    }
}