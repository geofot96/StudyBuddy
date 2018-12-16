package ch.epfl.sweng.studdybuddy.services.calendar;

import android.support.v7.widget.CardView;
import android.widget.GridLayout;

import java.util.List;

/**
 * Controller of the color of a targeted cell in the calendar
 */
public class ColorController {

    /**
     * go through the each cell of the calendar to update its color according
     * to the gradient
     *
     * @param calendarGrid the view of the calendar
     * @param groupAvailabilities the number of participant available for each time slot (cell of the calendar)
     * @param maxNumberOfUsers the maximum number of users the group can accept
     * @param CalendarWidth the width of the calendar
     */
    public void updateColor(GridLayout calendarGrid, List<Integer> groupAvailabilities, float maxNumberOfUsers, int CalendarWidth){
        int size = calendarGrid.getColumnCount() * calendarGrid.getRowCount();
        for (int i = 0; i < size; i++) {
            CardView cardView;
            if (i % CalendarWidth != 0) {//Hours shouldn't be clickable
                cardView = (CardView) calendarGrid.getChildAt(i);
                int index = (i / CalendarWidth) * (CalendarWidth - 1) + (i % CalendarWidth) - 1;
                cardView.setCardBackgroundColor(gradient((float) groupAvailabilities.get(index), maxNumberOfUsers));
            }
        }
    }

    /**
     * return the color picked in a dynamically set color gradient
     * according to the ratio of available users in the targeted time slot
     * the returned color is <tt>Color.WHITE</tt> when no one is available
     * and <tt>Color.GREEN</tt> when every one is available
     *
     * @param nAvailableUser the number of available users in this time slot
     * @return the right color according to the ration of available users
     */
    public int gradient(float nAvailableUser, float maxNumberOfUsers){
        float[] hsv = new float[3];
        android.graphics.Color.colorToHSV(android.graphics.Color.WHITE, hsv);
        float s = hsv[1];
        android.graphics.Color.colorToHSV(android.graphics.Color.GREEN, hsv);
        float coef = nAvailableUser/maxNumberOfUsers;
        hsv[1] = s +  coef * (hsv[1] - s);
        return android.graphics.Color.HSVToColor(hsv);
    }
}
