package ch.epfl.sweng.studdybuddy.core;

import java.util.UUID;

public final class Meeting {
    public SerialDate getDeadline() {
        return deadline;
    }

    public void setDeadline(SerialDate deadline) {
        this.deadline = deadline;
    }

    public SerialDate deadline;

    public ID<Meeting> getId() {
        return id;
    }

    public void setId(ID<Meeting> id) {
        this.id = id;
    }

    public ID<Meeting> id;

    public SerialDate getCreation() {
        return creation;
    }

    public void setCreation(SerialDate creation) {
        this.creation = creation;
    }

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
