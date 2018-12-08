package ch.epfl.sweng.studdybuddy.core;

import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

final public class User
{
    private String name;
    private ID<User> userID;
    private MeetingLocation favoriteLocation;
    private String favoriteLanguage;
    private String tokenID = "";
    public ID<User> getUserID()
    {
        return userID;
    }

    public void setUserID(ID<User> userID)
    {
        this.userID = userID;
    }


    public User(String name, ID<User> userId)
    {
        //this();
        this.name = name;
        this.userID = userId;
        this.favoriteLocation = MapsHelper.ROLEX_LOCATION;
        this.favoriteLanguage = "\uD83C\uDDEC\uD83C\uDDE7"; //GB emoji
    }

    public User(String name, String uId) {
        this.name = name;
        this.userID = new ID<>(uId);
        this.favoriteLocation = MapsHelper.ROLEX_LOCATION;
        this.favoriteLanguage = "\uD83C\uDDEC\uD83C\uDDE7"; //GB emoji
    }

    public User() {}


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public MeetingLocation getFavoriteLocation() {
        return favoriteLocation;
    }

    public void setFavoriteLocation(MeetingLocation favoriteLocation) {
        this.favoriteLocation = favoriteLocation;
    }

    public String getFavoriteLanguage() {
        return favoriteLanguage;
    }

    public void setFavoriteLanguage(String favoriteLanguage) {
        this.favoriteLanguage = favoriteLanguage;
    }


}
