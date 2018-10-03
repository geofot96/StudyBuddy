package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;

public class Group {
    private int participantNumber;
    private int maxParticipantNumber;
    private Course course;
    private ArrayList<User> participants;
    //private commonSchedule;
    // private groupChat;
    public Group(int participantNumber, int maxParticipantNumber, Course course, ArrayList<User> participants) {

        this.participantNumber = participantNumber;
        this.maxParticipantNumber = maxParticipantNumber;
        this.course = course;
        this.participants = participants;
    }

    public int getParticipantNumber() {
        return participantNumber;
    }

    public void setParticipantNumber(int participantNumber) {
        this.participantNumber = participantNumber;
    }

    public int getMaxParticipantNumber() {
        return maxParticipantNumber;
    }

    public void setMaxParticipantNumber(int maxParticipantNumber) {
        this.maxParticipantNumber = maxParticipantNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }
    public void addParticipant(User newParticipant)
    {
        if(participants.size()<maxParticipantNumber)
        {
            participants.add(newParticipant);
            newParticipant.getCurrentGroups().add(this);
        }
    }
    public void removeParticipant(User leavingParticipant)
    {
        if(participants.size()>=2&&participants.contains(leavingParticipant))
        {
            participants.remove(leavingParticipant);
        }
    }


}
