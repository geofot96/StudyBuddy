package ch.epfl.sweng.studdybuddy.core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.Language;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

/**
 * Class representing a user object
 */
@Entity
final public class User {
    private String name;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userID")
    private ID<User> userID;
    private MeetingLocation favoriteLocation;
    private String favoriteLanguage;

    /**
     * Getter for the id
     * @return the user's id
     */
    public ID<User> getUserID() {
        return userID;
    }

    /**
     * Setter for the id
     * @param userID the new id
     */
    public void setUserID(ID<User> userID) {
        this.userID = userID;
    }

    /**
     * Constructor for a User
     * @param name User's name
     * @param userId user's id
     */
    public User(String name, ID<User> userId) {
        this.name = name;
        this.userID = userId;
        this.favoriteLocation = MapsHelper.ROLEX_LOCATION;
        this.favoriteLanguage = Language.EN;
    }

    /**
     * Overridden method which check if two user are equal (all their fields are the same)
     * @param o Object to be compared to this
     * @return true if the two objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        User user = (User) o;
        return
            (o != null &&
            getClass() == o.getClass() &&
            name.equals(user.getName()) &&
            userID.equals(user.userID) &&
            favoriteLocation.equals(user.favoriteLocation) &&
            favoriteLanguage.equals(user.favoriteLanguage));


    }

    /**
     * Constructor for user
     * @param name the name of the user
     * @param uId the string literal representing the unique id of the user
     */
    public User(String name, String uId) {
        this.name = name;
        this.userID = new ID<>(uId);
        this.favoriteLocation = MapsHelper.ROLEX_LOCATION;
        this.favoriteLanguage = Language.EN;
    }

    /**
     * Empty constructor
     */
    public User() {
    }

    /**
     * Getter for the name
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the user's name
     * @param name the new name to be set
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the favorite meeting location of the user
     * @return the meeting location
     */
    public MeetingLocation getFavoriteLocation() {
        return favoriteLocation;
    }
    /**
     * Setter for the favorite meeting location of the user
     * @param favoriteLocation the new meeting location
     */
    public void setFavoriteLocation(MeetingLocation favoriteLocation) {
        this.favoriteLocation = favoriteLocation;
    }
    /**
     * Getter for the favorite language of the user
     * @return the favorite language
     */
    public String getFavoriteLanguage() {
        return favoriteLanguage;
    }
    /**
     * Setter for the favorite language of the user
     * @param favoriteLanguage the new favorite language
     */
    public void setFavoriteLanguage(String favoriteLanguage) {
        this.favoriteLanguage = favoriteLanguage;
    }

    /**
     * Sets all this user's field to those of another one
     * @param user the user to be copied
     */
    public void setAs(User user) {
        if (user != null) {
            setFavoriteLocation(user.getFavoriteLocation());
            setFavoriteLanguage(user.getFavoriteLanguage());
            setName(user.getName());
            setUserID(new ID<>(user.getUserID().getId()));

        }
    }

}
