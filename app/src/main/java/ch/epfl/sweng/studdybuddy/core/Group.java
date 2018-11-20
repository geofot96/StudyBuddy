package ch.epfl.sweng.studdybuddy.core;

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

    private ID<Group> groupID; //TODO add getters and setters
    private String lang;

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    private String adminID;

    public SerialDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(SerialDate creationDate) {
        this.creationDate = creationDate;
    }

    private SerialDate creationDate;
    //TODO add schedule and Chat
    //private commonSchedule;
    //private groupChat;

    public Group() {}


    public Group(int maxNoUsers, Course course, String lang, String adminID) {
        this(maxNoUsers, course, lang, UUID.randomUUID().toString(), adminID);
    }

    public Group(int maxNoUsers, Course course, String lang, String gId, String adminID) {
        this();
        if(maxNoUsers <= 0) {
            throw new IllegalArgumentException("Participants number must be > 0 and maximum number of participants must be positive");
        }
        this.groupID = new ID<>(gId);
        this.maxNoUsers = maxNoUsers;
        this.course = course;
        this.lang = lang;
        this.creationDate = new SerialDate();
        this.adminID = adminID;
    }

    public ID<Group> getGroupID()
    {
        return new ID<>(groupID);
    }

    public void setGroupID(ID<Group> groupID)
    {
        this.groupID = groupID;
    }


    public int getMaxNoUsers()
    {
        return maxNoUsers;
    }

    public void setMaxNoUsers(int maxNoUsers)
    {
        if(maxNoUsers <= 0)
        {
            throw new IllegalArgumentException("Maximum number of participants must be positive");
        }
        this.maxNoUsers = maxNoUsers;
    }

    public Course getCourse()
    {
        return new Course(this.course);
    }

    public void setCourse(Course course)
    {
        this.course = new Course(course);
    }

    public String getLang()
    {
        return lang;
    }

    public void setLang(String language)
    {
        this.lang = language;
    }

    public Group withAdmin(String adminID) {
        return new Group(maxNoUsers, course, lang, groupID.getId(), adminID);
    }

    public void copy(Group group) {
        setCourse(group.getCourse());
        setLang(group.getLang());
        setAdminID(group.getAdminID());
        setMaxNoUsers(group.getMaxNoUsers());
        setGroupID(group.getGroupID());

    }

    @Override
    public int compareTo(Group group)
    {
        if(this.getCreationDate().before(group.getCreationDate()))
        {
            return 1;
        }
        else if(this.getCreationDate().after(group.getCreationDate()))
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
