package ch.epfl.sweng.studdybuddy;

import java.util.UUID;

public class UserGroupJoin {
    private ID<UserGroupJoin> id;
    private String groupID;
    private String userID;


    public UserGroupJoin(){}

    public UserGroupJoin(ID<UserGroupJoin> id, String groupID, String userID) {
        this.id = id;
        this.groupID = groupID;
        this.userID = userID;
    }

    public UserGroupJoin(String groupID, String userID) {
        this.id = new ID<UserGroupJoin>(UUID.randomUUID().toString());
        this.groupID = groupID;
        this.userID = userID;
    }
    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public ID<UserGroupJoin> getId() {
        return id;
    }
}
