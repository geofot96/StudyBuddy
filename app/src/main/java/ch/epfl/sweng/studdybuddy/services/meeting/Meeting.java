package ch.epfl.sweng.studdybuddy.services.meeting;

import java.util.Date;
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;

public final class Meeting {
    private ID<Meeting> id;
    private long ending;
    private long starting;
    private MeetingLocation location;

    public Meeting(long creation, long deadline, String id) {
        this.starting = creation;
        this.ending = deadline;
        this.id = new ID<>(id);
        this.location = MapsHelper.ROLEX_LOCATION;
    }

    //not used
    /*public void copy(Meeting copy) {
        this.setStarting(copy.getStarting());
        this.setEnding(copy.getEnding());
        this.setId(copy.getId());
    }*/

    public Meeting() {
        this(UUID.randomUUID().toString());
    }

    public Meeting(String id) {
        this(new Date().getTime(), new Date().getTime(), new MeetingLocation(), id);
    }

    public Meeting(Long creation, Long deadline, MeetingLocation location, String id) {
        this.ending = deadline;
        this.id = new ID<>(id);
        this.starting = creation;
        this.location = location;
    }

    public Meeting(Long starting, Long ending){
        this(starting, ending, new MeetingLocation(), UUID.randomUUID().toString());
    }

    public long getEnding() {
        return ending;
    }
    public void setEnding(long deadline) {
        this.ending = deadline;
    }

    public ID<Meeting> getId() {
        return id;
    }
    public void setId(ID<Meeting> id) {
        this.id = id;
    }


    public long getStarting() {
        return starting;
    }
    public void setStarting(long creation) {
        this.starting = creation;
    }

    public MeetingLocation getLocation(){return location;}
    public void setLocation(MeetingLocation meetingLocation){this.location = meetingLocation;}


}
