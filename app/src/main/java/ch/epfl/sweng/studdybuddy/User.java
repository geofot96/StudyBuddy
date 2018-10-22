package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final public class User
{
    private String name;
    private ID<User> userID;


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
        this.name = name;
        this.userID = userId;
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

}
