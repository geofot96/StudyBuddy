package ch.epfl.sweng.studdybuddy;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * class representing a group
 * participantNumber is the current number of participants
 * maxNoUsers is the maximum capacity of the group
 * course is the course for which the group is created
 * participants is the actual group members
 */
public class Group
{
    private int maxNoUsers;
    private Course course;
    private List<User> participants;
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


    public Group(int maxNoUsers, Course course, String lang, List<User> participants)
    {
        if(maxNoUsers <= 0)
        {
            throw new IllegalArgumentException("Participants number must be > 0 and maximum number of participants must be positive");
        }


        if(participants.size() > maxNoUsers)
        {
            throw new IllegalArgumentException("You can't have more than the maximum number of participants");
        }

        if(maxNoUsers < participants.size())
        {
            throw new IllegalArgumentException("Max number of participants can't be less than actual number of participants");
        }

        this.groupID = new ID<>(UUID.randomUUID().toString());
        this.maxNoUsers = maxNoUsers;
        this.course = course;
        this.participants = participants;
        this.language = lang;
        this.creationDate = new SerialDate();
    }

    public Group(Group sourceGroup)
    {
        //TODO why do we need this constructor and what do we do with the date
        this.course = sourceGroup.getCourse();
        this.participants = new ArrayList<>(sourceGroup.participants);
        this.maxNoUsers = sourceGroup.getMaxNoUsers();
        this.language = sourceGroup.language;
    }

    public String getGroupID()
    {
        return groupID.getId();
    }

    public void setGroupID(String groupID)
    {
        this.groupID = new ID<>(groupID);
    }

    public int getParticipantNumber()
    {
        if(participants != null)
        return participants.size();
        else
            return 0;
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

    public List<User> getParticipants()
    {
        return new ArrayList<>(participants); //TODO return a collections.unmodifiableList
    }

    public void setParticipants(List<User> participants)
    {
        this.participants = new ArrayList<>(participants);
    }

    public String getLang()
    {
        return language;
    }

    public void setLang(String language)
    {
        this.language = language;
    }

    public void addParticipant(User newParticipant)
    {
        if(participants.size() < maxNoUsers)
        {
            participants.add(newParticipant);
            List<Group> currentGroups = newParticipant.getCurrentGroups();
            if(currentGroups != null)
                currentGroups.add(this);
        }
        else
        {
            throw new IllegalArgumentException("You have attended the maximum number of participants");
        }
        //TODO handle this case differently?
    }

    public void removeParticipant(User leavingParticipant)
    {
        if(participants.size() >= 2 && participants.contains(leavingParticipant))
        {
            participants.remove(leavingParticipant);
        }
    }

    @Override
    public String toString() {
        return "Group: " + maxNoUsers + " " + course.getCourseName() + " " + groupID.getId();
    }
}
