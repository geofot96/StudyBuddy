package ch.epfl.sweng.studdybuddy.core;

import java.io.Serializable;

public class MeetingLocation implements Serializable{

    private  String title;
    private  double latitude;
    private  double longitude;

    public MeetingLocation(){

    }

    public MeetingLocation(String title, double latitude, double Longitude){

        this.title = title;
        this.latitude = latitude;
        longitude = Longitude;
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
}
