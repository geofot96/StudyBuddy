package ch.epfl.sweng.studdybuddy.core;

import java.util.UUID;

import ch.epfl.sweng.studdybuddy.util.MapsHelper;

public final class Meeting {


    public Date deadline;

    public ID<Meeting> getId() {
        return id;
    }
    public ID<Meeting> id;
    public MeetingLocation location;



    public Date creation;
    public Meeting() {
        this(UUID.randomUUID().toString());
    }

    public Meeting(String id) {
        this(new SerialDate(), new SerialDate(), id);
    }

    public Meeting(Date creation, Date deadline, String id) {
        this.deadline = deadline;
        this.id = new ID<>(id);
        this.creation = creation;
        this.location = MapsHelper.ROLEX_LOCATION;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setId(ID<Meeting> id) {
        this.id = id;
    }


    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public MeetingLocation getLocation() {
        return location;
    }

    public void setLocation(MeetingLocation location){
        this.location = location;
    }

    public void copy(Meeting copy) {
        this.setDeadline(copy.getDeadline());
        this.setCreation(copy.getCreation());
        this.setId(copy.getId());
    }

    @Override
    public String toString() {
        return deadline.toString();
    }

    public String date() {
        return deadline.toString();
    }

    public String time() {
        return deadline.getHour() + ":" + deadline.getMinutes();
    }
}
