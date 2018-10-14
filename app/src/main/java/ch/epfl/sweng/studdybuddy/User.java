package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User
{
    private String email;
    private String name;
    private String Section;
    //private Schedule schedule;
    private List<Group> currentGroups;
    private List<User> friendList;
    private ID<User> userID;

    public ID<User> getUserID()
    {
        return userID;
    }

    public void setUserID(ID<User> userID)
    {
        this.userID = userID;
    }

    public User(String email, String name, String section, List<Group> currentGroups, List<User> friendList)
    {
        this.email = email;
        this.name = name;
        this.Section = section;
        this.currentGroups = new ArrayList<>(currentGroups);
        this.friendList = new ArrayList<>(friendList);
        this.userID = new ID<>(UUID.randomUUID().toString());

    }

    public User() {}


    public String getEmail()
    {//TODO TEST
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSection()
    {
        return Section;
    }

    public void setSection(String section)
    {
        Section = section;
    }

    public List<Group> getCurrentGroups()
    {
        if(currentGroups != null)
        {
            return new ArrayList<>(currentGroups);
        }
        else
        {
            return null;
        }
    }

    public List<User> getFriendList()
    {
        return friendList;
    }

    public void setFriendList(List<User> friendList)
    {
        if(friendList != null)
        {
            this.friendList = new ArrayList<>(friendList);
        }

    }

    //TODO why do we want this method?? Maybe have an add to group method or something like that?
    public void setCurrentGroups(List<Group> currentGroups)
    {
        this.currentGroups = currentGroups;
    }
}
