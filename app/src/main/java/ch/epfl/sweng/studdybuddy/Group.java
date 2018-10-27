package ch.epfl.sweng.studdybuddy;

import java.util.UUID;

/**
 * class representing a group
 * participantNumber is the current number of participants
 * maxNoUsers is the maximum capacity of the group
 * course is the course for which the group is created
 * participants is the actual group members
 */
public class Group implements Comparable<Group> {
    private int maxNoUsers;
    private Course course;

    private ID<Group> groupID; //TODO add getters and setters
    private String language;
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


    public Group(int maxNoUsers, Course course, String lang)
    {
        this(maxNoUsers, course, lang, UUID.randomUUID().toString());
    }

    public Group(int maxNoUsers, Course course, String lang, String gId)
    {
        this();
        if(maxNoUsers <= 0)
        {
            throw new IllegalArgumentException("Participants number must be > 0 and maximum number of participants must be positive");
        }


        this.groupID = new ID<>(gId);
        this.maxNoUsers = maxNoUsers;
        this.course = course;
        this.language = lang;
        this.creationDate = new SerialDate();
    }

    /*public Group(Group sourceGroup)
    {
        this();
        //TODO why do we need this constructor and what do we do with the date
        this.course = sourceGroup.getCourse();
        this.maxNoUsers = sourceGroup.getMaxNoUsers();
        this.language = sourceGroup.language;
        this.creationDate = new SerialDate();
    }*/


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
        return language;
    }

    public void setLang(String language)
    {
        this.language = language;
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
