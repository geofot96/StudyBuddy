package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;

public class Group
{
    private int participantNumber;
    private int maxParticipantNumber;
    private Course course;
    private ArrayList<User> participants;

    //private commonSchedule;
    // private groupChat;
    public Group(int participantNumber, int maxParticipantNumber, Course course, ArrayList<User> participants)
    {

        if(participantNumber < 0 || maxParticipantNumber <= 0)
        {
            throw new IllegalArgumentException("Participants number must be > 0 and maximum number of participants must be positive");
        }
        //TODO do we want to throw an exception here or just put the values to 0 and 1?

        if(participants.size() > maxParticipantNumber)
        {
            throw new IllegalArgumentException("You can't have more than the maximum number of participants");
        }
        //TODO ask Gerald
        if(maxParticipantNumber < participantNumber)
        {
            throw new IllegalArgumentException("Max number of participants can't be less than actual number of participants");
        }

        this.participantNumber = participantNumber;
        this.maxParticipantNumber = maxParticipantNumber;
        this.course = course;
        this.participants = participants;
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

    public Course getCourse()
    {
        return course;
    }

    public void setCourse(Course course)
    {
        this.course = course;// TODO maybe use "safe" copy
    }

    public ArrayList<User> getParticipants()
    {
        return participants;
    }

    public void setParticipants(ArrayList<User> participants)
    {
        this.participants = participants;
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
        //TODO handle this case
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
