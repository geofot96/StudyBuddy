package ch.epfl.sweng.studdybuddy;

import java.util.List;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.Group;

interface DatabaseWrapper {

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
    public Group getGroup(GroupId id);

    /**
     * The list of all groups stored in the database
     * @return the list of groups
     */
    public List<Group> getAllGroups();

    /**
     * Updates a group
     * @throws IllegalArgumentException if the group id is invalid
     * @param groupId the group id
     * @param newGroup  the new group's value
     */
    public void setGroup(GroupId groupId, Group newGroup);


    /**
     * Puts the group in the database
     * @param newGroup the group to add
     */
    public void putGroup(Group newGroup);

    /**
     * Removes the group from the database
     * @throws IllegalArgumentException if the group id is invalid
     * @param groupId the groupID
     */
    public void removeGroup(GroupId groupId);

    /**
     * Returns all the groups from a user
     * @throws IllegalArgumentException if the user id is invalid
     * @param userID the userID
     * @return the user groups as a list
     */
    public List<Group> getUserGroups(UserId userID);

    /**
     * Returns the list of all meetings from a user
     * @throws IllegalArgumentException if the user id is invalid
     * @param userId the user id
     * @return the user meetings
     */
    public List<Meeting> getMeetings(UserId userId);

    /**
     * Returns the list of friends of a user
     * @throws IllegalArgumentException if the user id is invalid
     * @param userID the user id
     * @return the user friends
     */
    public List<Friendship> getFriends(UserId userID);

    /**
     * Puts a Friendship in the database
     * @throws IllegalArgumentException if the group id doesn't exist
     * @param userID the userId
     */
    public void putFriendShip(Friendship friendship);


    /**
     * Puts a meeting in the database
     * @param meeting the meeting to put
     */
    public void putMeeting(Meeting meeting);

    /**
     * Removes a meeting in the database
     * @throws IllegalArgumentException if the meeting id is invalid
     * @param meetingIdid the meeting to remove
     */
    public void deleteMeeting(MeetingId meetingId);

    /**
     * Modifies a meeting in the database
     * @param meeting the meeting to modify
     */
    public void setMeeting(MeetingId meetingId, Meeting meeting);

}
