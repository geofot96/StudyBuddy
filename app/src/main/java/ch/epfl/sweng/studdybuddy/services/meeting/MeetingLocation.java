package ch.epfl.sweng.studdybuddy.services.meeting;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

import ch.epfl.sweng.studdybuddy.util.Messages;

public class MeetingLocation implements Serializable{

    private  String title = new String();
    private String address = new String();
    private  double latitude;
    private  double longitude;

    public MeetingLocation(){

    }

    public MeetingLocation(Bundle data) {
        this(
                data.getString(Messages.LOCATION_TITLE),
                data.getString(Messages.ADDRESS),
                data.getDouble(Messages.LATITUDE, 0),
                data.getDouble(Messages.LONGITUDE, 0)
        );
    }

    @Override
    public boolean equals(Object o) {
        //if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingLocation that = (MeetingLocation) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                title.equals(that.title) &&
                address.equals(that.address);
    }


    public MeetingLocation(String title, String address, double latitude, double longitude){
        this();
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return getTitle() + ": " + getAddress();
    }

}
