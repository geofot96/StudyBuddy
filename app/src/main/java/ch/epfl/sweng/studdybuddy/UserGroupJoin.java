package ch.epfl.sweng.studdybuddy;

import java.util.UUID;

public class UserGroupJoin {
    public ID<UserGroupJoin> getId() {
        return id;
    }

    public void setId(ID<UserGroupJoin> id) {
        this.id = id;
    }

    public ID<Group> getGroupID() {
        return groupID;
    }

    public void setGroupID(ID<Group> groupID) {
        this.groupID = groupID;
    }

    public ID<User> getUserID() {
        return userID;
    }

    public void setUserID(ID<User> userID) {
        this.userID = userID;
    }

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
        setGroupID(new ID<>(groupID));
        setUserID(new ID<>(userID));

    }

    public UserGroupJoin(ID<Group> groupID, ID<User> userID) {
        setGroupID(groupID);
        setUserID(userID);
        this.id = new ID<UserGroupJoin>(Integer.toString(hashCode()));

    }

    @Override
    public int hashCode() {
        return (groupID.toString() + getUserID().toString()).hashCode();
    }
}
