package ch.epfl.sweng.studdybuddy.core;

import java.util.Date;
import java.util.UUID;

/**
 * class representing a group
 * participantNumber is the current number of participants
 * maxNoUsers is the maximum capacity of the group
 * course is the course for which the group is created
 * participants is the actual group members
 */
public final class Group implements Comparable<Group> {
    private int maxNoUsers;
    private Course course;
    private ID<Group> groupID;
    private String lang;
    private long creationDate;
    private String adminID;

    /**
     * Getter for the Id of the admin
     * @return  the id of the admin of the group
     */
    public String getAdminID() {
        return adminID;
    }
    /**
     * Setter for the Id of the admin
     */
    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    /**
     * Getter for the creation date
     * @return  the creation date of the group
     */
    public long getCreationDate() {
        return creationDate;
    }

    /**
     * Empty constructor
     */
    public Group() {
    }

    /**
     * Constructs a group object using all needed parameters except of the ID of the group, which is assigned randomly
     * @param maxNoUsers The maximal number of participants the group can have
     * @param course The course related to the group
     * @param lang The language that will be used in the group
     * @param adminID The id of the group's id
     */
    public Group(int maxNoUsers, Course course, String lang, String adminID) {
        this(maxNoUsers, course, lang, UUID.randomUUID().toString(), adminID);
    }

    /**
     * Constructs a group object using all needed parameters
     * @param maxNoUsers The maximal number of participants the group can have
     * @param course The course related to the group
     * @param gId The unique ID assigned to the group
     * @param lang The language that will be used in the group
     * @param adminID The id of the group's id
     */
    public Group(int maxNoUsers, Course course, String lang, String gId, String adminID) {
        this();
        if (maxNoUsers <= 0) {
            throw new IllegalArgumentException("Participants number must be > 0 and maximum number of participants must be positive");
        }
        this.groupID = new ID<>(gId);
        this.maxNoUsers = maxNoUsers;
        this.course = course;
        this.lang = lang;
        this.creationDate = new Date().getTime();
        this.adminID = adminID;
    }
    /**
     * Getter for the Id of the group
     * @return  the id of the group
     */
    public ID<Group> getGroupID() {
        return new ID<>(groupID);
    }

    /**
     * Setter for the ID of the group
     * @param groupID the new ID to be set
     */
    public void setGroupID(ID<Group> groupID) {
        this.groupID = groupID;
    }

    /**
     * Getter for the maximum number of participants
     * @return  the maximal number of users in the group
     */
    public int getMaxNoUsers() {
        return maxNoUsers;
    }

    /**
     * Set the maximum number of participants
     * @param maxNoUsers the new maximal number of users in the group to be set
     */
    public void setMaxNoUsers(int maxNoUsers) {
        if (maxNoUsers <= 0) {
            throw new IllegalArgumentException("Maximum number of participants must be positive");
        }
        this.maxNoUsers = maxNoUsers;
    }
    /**
     * Getter for Course related to the group
     * @return  the Course of the group
     */
    public Course getCourse() {
        return new Course(this.course);
    }

    /**
     * Setter for course the group
     * @param course the new course to be set
     */
    public void setCourse(Course course) {
        this.course = new Course(course);
    }
    /**
     * Getter for Language used in the group
     * @return  the language of the group
     */
    public String getLang() {
        return lang;
    }

    /**
     * Setter for the language of the group
     * @param language the new language to be set
     */
    public void setLang(String language) {
        this.lang = language;
    }

    /**
     * Return a new Group using the same characteristics as the current one, but with a new adminID
     * @param adminID the admin Id of the new group
     * @return the newly created group
     */
    public Group withAdmin(String adminID) {

        return new Group(maxNoUsers, course, lang, groupID.getId(), adminID);
    }


    /**
     * An overriden method used when comparing groups. The precedence is given to the most recently created one
     * @param group the group to be compared with the current one
     * @return 1 if this group is more recent, -1 if the parameter is more recent and 0 if they are identical
     */
    @Override
    public int compareTo(Group group) {
        if (this.getCreationDate() < (group.getCreationDate())) {
            return 1;
        } else if (this.getCreationDate() > (group.getCreationDate())) {
            return -1;
        } else {
            return 0;
        }
    }
}
