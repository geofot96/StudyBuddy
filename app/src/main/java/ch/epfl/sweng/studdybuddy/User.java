package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final public class User
{
    private String name;
    private ID<User> userID;

    public List<String> getCoursesPreset() {
        return coursesPreset;
    }

    public void setCoursesPreset(List<String> coursesPreset) {
        this.coursesPreset = coursesPreset;
    }

    private List<String> coursesPreset;

    public ID<User> getUserID()
    {
        return userID;
    }

    public void setUserID(ID<User> userID)
    {
        this.userID = userID;
    }

    public User(String name, ID<User> userId, List<String> coursesPreset)
    {
        this.name = name;
        this.userID = userId;
        this.coursesPreset = coursesPreset;
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
