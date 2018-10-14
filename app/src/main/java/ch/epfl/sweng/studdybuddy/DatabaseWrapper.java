package ch.epfl.sweng.studdybuddy;

import java.util.List;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.Group;

public interface DatabaseWrapper {

    /**
     * This interface describes the database core API
     */

    /**
     * Returns the list of all courses stored in the database
     * @return the list courses
     */
    public List<Course> getCourses();



    /**
     * Returns a specific group stored in the database
     * @throws IllegalArgumentException if the group id is invalid
     * @param id the GroupId
     * @return the Group
     */
    public Group getGroup(ID<Group> id);

    /**
     * The list of all groups stored in the database
     * @return the list of groups
     */
    public List<Group> getAllGroups();

    /**
     * Updates a group
     * @throws IllegalArgumentException if the group id is invalid
     * @param id group to modify
     * @param newGroup  the new group's value
     */
    public void setGroup(ID<Group> groupId, Group group);


    /**
     * Puts the group in the database
     * @param newGroup the group to add
     */
    public void putGroup(Group group);

    /**
     * Removes the group from the database
     * @throws IllegalArgumentException if the group id is invalid
     * @param id removed group id
     */
    public void removeGroup(ID<Group> groupId);

    /**
     * Returns all the groups from a user
     * @throws IllegalArgumentException if the user id is invalid
     * @param id the userID
     * @return the user groups as a list
     */
    public List<Group> getUserGroups(ID<User> userID);

    /**
     * Returns the list of all meetings from a user
     * @throws IllegalArgumentException if the user id is invalid
     * @param id the user id
     * @return the user meetings
     */
    public List<Meeting> getMeetings(ID<User> id);

    /**
     * Returns the list of friends of a user
     * @throws IllegalArgumentException if the user id is invalid
     * @param id the user id
     * @return the user friends
     */
    public List<Friendship> getFriends(ID<User> id);

    /**
     * Puts a Friendship in the database
     * @param friendship the friendship to add
     */
    public void putFriendShip(Friendship friendship);

    /**
     * Removes a Friendship from the database
     * @throws IllegalArgumentException if the group id doesn't exist
     * @param id removed friendship id
     */
    public void deleteFriendShip(ID<Friendship> id);
    /**
     * Puts a meeting in the database
     * @param meeting the meeting to put
     */
    public void putMeeting(Meeting meeting);

    /**
     * Removes a meeting in the database
     * @throws IllegalArgumentException if the meeting id is invalid
     * @param id removed meeting id
     */
    public void deleteMeeting(ID<Meeting> id);

    /**
     * Modifies a meeting in the database
     * @param meeting the meeting to modify
     */
    public void setMeeting(ID<Meeting> id, Meeting meeting);

}
