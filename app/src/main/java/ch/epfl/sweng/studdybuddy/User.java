package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;
import java.util.UUID;

public class User
{
    private String email;
    private String name;
    private @DummyCourses.Section
    String Section;
    //private Schedule schedule;
    private ArrayList<Group> currentGroups;
    private ArrayList<User> friendList;
    private UUID userID;

    public UUID getUserID()
    {
        return userID;
    }

    public void setUserID(UUID userID)
    {
        this.userID = userID;
    }

    public User(String email, String name, String section, ArrayList<Group> currentGroups, ArrayList<User> friendList)
    {
        this.email=email;
        this.name = name;
        this.Section = section;
        this.currentGroups = new ArrayList<>(currentGroups);
        this.friendList=new ArrayList<>(friendList);
        this.userID = UUID.randomUUID();

    }

    public String getEmail() {//TODO TEST
        return email;
    }

    public void setEmail(String email) {
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

    public ArrayList<Group> getCurrentGroups()
    {
        return new ArrayList<>(currentGroups);
    }

    public ArrayList<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<User> friendList) {
        this.friendList = new ArrayList<>(friendList);
    }

    //TODO why do we want this method?? Maybe have an add to group method or something like that?
    public void setCurrentGroups(ArrayList<Group> currentGroups)
    {
        this.currentGroups = currentGroups;
    }
}
