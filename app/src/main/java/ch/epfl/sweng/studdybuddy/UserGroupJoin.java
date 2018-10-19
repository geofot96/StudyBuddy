package ch.epfl.sweng.studdybuddy;

import java.util.UUID;

public class UserGroupJoin {
    private ID<UserGroupJoin> id;
    private ID<Group> groupID;
    private ID<User> userID;


    public UserGroupJoin(){}

    public UserGroupJoin(ID<UserGroupJoin> id, String groupID, String userID) {
        this.id = id;
        this.groupID = new ID<Group>(groupID);
        this.userID = new ID<User>(userID);
    }

    public UserGroupJoin(String groupID, String userID) {
        this.id = new ID<UserGroupJoin>(UUID.randomUUID().toString());
        setGroupID(groupID);
        setUserID(userID);
    }
    public ID<Group> getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = new ID<Group>(groupID);
    }

    public ID<User> getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = new ID<User>(userID);
    }


    public ID<UserGroupJoin> getId() {
        return id;
    }
}
