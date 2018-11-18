package ch.epfl.sweng.studdybuddy.core;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class MeetingLocation implements Serializable{

    private  String title;
    private String address;
    private  double latitude;
    private  double longitude;

    public MeetingLocation(){

    }

    public MeetingLocation(String title, String address, double latitude, double Longitude){

        this.title = title;
        this.latitude = latitude;
        longitude = Longitude;
        this.address = address;
    }

    public MeetingLocation(String title, String address , LatLng pos){
        this(title, address, pos.latitude, pos.longitude);
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    
    public void setTitle(String title){
        this.title = title;
    }


    public  LatLng getLatLng() {
        return new LatLng(getLatitude(), getLongitude());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
