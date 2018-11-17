package ch.epfl.sweng.studdybuddy.core;

import java.util.UUID;

import ch.epfl.sweng.studdybuddy.util.Helper;

public final class Meeting {


    public SerialDate deadline;

    public ID<Meeting> getId() {
        return id;
    }
    public ID<Meeting> id;
    public MeetingLocation location;



    public SerialDate creation;
    public Meeting() {
        this(UUID.randomUUID().toString());
    }

    public Meeting(String id) {
        this(new SerialDate(), new SerialDate(), id);
    }

    public Meeting(SerialDate creation, SerialDate deadline, String id) {
        this.deadline = deadline;
        this.id = new ID<>(id);
        this.creation = creation;
        this.location = Helper.ROLEX_LOCATION;
    }

    public SerialDate getDeadline() {
        return deadline;
    }

    public void setDeadline(SerialDate deadline) {
        this.deadline = deadline;
    }

    public void setId(ID<Meeting> id) {
        this.id = id;
    }


    public SerialDate getCreation() {
        return creation;
    }

    public void setCreation(SerialDate creation) {
        this.creation = creation;
    }

}
