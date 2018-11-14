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

    ID<Meeting> id;
    public Meeting() {
        this(UUID.randomUUID().toString());
    }

    public Meeting(String id) {
        this.deadline = new SerialDate();
        this.id = new ID<>(id);
    }
}
