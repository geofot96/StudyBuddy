package ch.epfl.sweng.studdybuddy.services.meeting;

import java.util.Date;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.SerialDate;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

public final class Meeting {
    private ID<Meeting> id;
    private Date ending;
    private Date starting;
    private MeetingLocation location;


    public Meeting(Date creation, Date deadline, String id) {
        this.starting = creation;
        this.ending = deadline;
        this.id = new ID<>(id);
        this.location = MapsHelper.ROLEX_LOCATION;
    }


    public void copy(Meeting copy) {
        this.setStarting(copy.getStarting());
        this.setEnding(copy.getEnding());
        this.setId(copy.getId());
    }

    public Meeting() {
        this(UUID.randomUUID().toString());
    }

    public Meeting(String id) {
        this(new Date(), new Date(), new MeetingLocation(), id);
    }

    public Meeting(Date creation, Date deadline, MeetingLocation location, String id) {
        this.ending = deadline;
        this.id = new ID<>(id);
        this.starting = creation;
        this.location = location;
    }

    public Date getEnding() {
        return ending;
    }
    public void setEnding(Date deadline) {
        this.ending = deadline;
    }

    public ID<Meeting> getId() {
        return id;
    }
    public void setId(ID<Meeting> id) {
        this.id = id;
    }


    public Date getStarting() {
        return starting;
    }
    public void setStarting(Date creation) {
        this.starting = creation;
    }

    public MeetingLocation getLocation(){return location;}
    public void setLocation(MeetingLocation meetingLocation){this.location = meetingLocation;}

}
