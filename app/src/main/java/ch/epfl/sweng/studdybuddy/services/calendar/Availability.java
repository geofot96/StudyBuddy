package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.List;

/**
 * An Availability is the user availabilities for a unique group
 * The availabilities is represented in a {@link List<Boolean>}
 * where each <tt>Boolean</tt> represent an availability in a particular
 * slot of the calendar.
 */
public interface Availability{
    /**
     *
     * @return the availabilities of the user as a <tt>List<Boolean></tt>
     */
    List<Boolean> getUserAvailabilities();

    /**
     *
     * @param row of the calendar's gridView
     * @param column of the calendar's gridView
     * @return <tt>true</tt> if the user is available at this slot
     * @throws ArrayIndexOutOfBoundsException if the parameters don't match
     * the list of availabilities
     */
    Boolean isAvailable(int row, int column) throws ArrayIndexOutOfBoundsException;

    /**
     *
     * @param row of the calendar's gridView
     * @param column of the calendar's gridView
     * @throws ArrayIndexOutOfBoundsException if the parameters don't match
     * the list of availabilities
     */
    void modifyAvailability(int row, int column) throws ArrayIndexOutOfBoundsException;
}
