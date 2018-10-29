package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.List;

public interface Availability{
    void addAvailability(int row, int column) throws ArrayIndexOutOfBoundsException;
    void removeAvailability(int row, int column) throws ArrayIndexOutOfBoundsException;
    List<Boolean> getUserAvailabilities();
    Boolean isAvailable(int row, int column);
}
