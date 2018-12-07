package ch.epfl.sweng.studdybuddy.core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;
@Entity
final public class User
{
    private String name;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userID")
    private ID<User> userID;
    private MeetingLocation favoriteLocation;
    private String favoriteLanguage;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  name.equals(user.name) &&
                userID.equals(user.userID) &&
                favoriteLocation.equals(user.favoriteLocation) &&
                favoriteLanguage.equals(user.favoriteLanguage);
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
