package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;

/**
 * class representing a group
 * participantNumber is the current number of participants
 * maxParticipantNumber is the maximum capacity of the group
 * course is the course for which the group is created
 * participants is the actual group members
 */
public class Group
{
    private int participantNumber;
    private int maxParticipantNumber;
    private Course course;
    private ArrayList<User> participants;

    //private commonSchedule;
    // private groupChat;

    //TODO create a copy constructor to make the getters safe

    public Group(int maxParticipantNumber, Course course, ArrayList<User> participants)
    {
        this.participantNumber = participants.size();
        if(maxParticipantNumber <= 0)
        {
            throw new IllegalArgumentException("Participants number must be > 0 and maximum number of participants must be positive");
        }


        if(participants.size() > maxParticipantNumber)
        {
            throw new IllegalArgumentException("You can't have more than the maximum number of participants");
        }

        if(maxParticipantNumber < participantNumber)
        {
            throw new IllegalArgumentException("Max number of participants can't be less than actual number of participants");
        }

        this.maxParticipantNumber = maxParticipantNumber;
        this.course = course;
        this.participants = participants;
    }
    public Group(Group sourceGroup) throws CloneNotSupportedException {
        this.course=new Course(sourceGroup.getCourse());
        this.participants=new ArrayList<>(sourceGroup.participants);
        this.participantNumber=sourceGroup.getParticipantNumber();
        this.maxParticipantNumber=sourceGroup.getMaxParticipantNumber();
    }

    public int getParticipantNumber()
    {
        return participantNumber;
    }

    public void increaseParticipantNumber()
    {
        if(this.participantNumber < maxParticipantNumber)
            this.participantNumber++;
        else
            //TODO handle this case
            throw new IllegalArgumentException("You can't have more than Max Num participants");
    }

    public int getMaxParticipantNumber()
    {
        return maxParticipantNumber;
    }

    public void setMaxParticipantNumber(int maxParticipantNumber)
    {
        if(maxParticipantNumber <= 0)
        {
            throw new IllegalArgumentException("Maximum number of participants must be positive");
        }
        this.maxParticipantNumber = maxParticipantNumber;
    }

    public Course getCourse(){
        return new Course(this.getCourse());
    }

    public void setCourse(Course course){
        this.course = new Course(course);
    }

    public ArrayList<User> getParticipants()
    {
        return new ArrayList<>(participants);
    }

    public void setParticipants(ArrayList<User> participants)
    {
        this.participants = new ArrayList<>(participants);
    }

    public void addParticipant(User newParticipant)
    {
        if(participants.size() < maxParticipantNumber)
        {
            participants.add(newParticipant);
            ArrayList<Group> currentGroups = newParticipant.getCurrentGroups();
            if(currentGroups != null)
                currentGroups.add(this);
            this.participantNumber += 1;
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

    public void showParticipants() // TODO add a method to return the User Objects
    {
        for(int i = 0; i < this.participantNumber; i++)
        {
            System.out.println(participants.get(i).getName() + " " + participants.get(i).getSection());
        }
    }


}
