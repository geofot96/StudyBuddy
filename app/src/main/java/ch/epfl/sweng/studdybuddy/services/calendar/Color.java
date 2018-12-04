package ch.epfl.sweng.studdybuddy.services.calendar;

import android.support.v7.widget.CardView;
import android.widget.GridLayout;

import java.util.List;

public class Color {
    public static void updateColor(GridLayout calendarGrid, List<Integer> av, float NmaxUsers, int CalendarWidth){
        int size = calendarGrid.getColumnCount() * calendarGrid.getRowCount();
        for (int i = 0; i < size; i++) {
            CardView cardView;
            if (i % CalendarWidth != 0) {//Hours shouldn't be clickable
                cardView = (CardView) calendarGrid.getChildAt(i);
                int index = (i / CalendarWidth) * (CalendarWidth - 1) + (i % CalendarWidth) - 1;
                cardView.setCardBackgroundColor(gradient((float) av.get(index), NmaxUsers));
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
    private static int gradient(float nAvailableUser, float NmaxUsers){
        float[] hsv = new float[3];
        android.graphics.Color.colorToHSV(android.graphics.Color.WHITE, hsv);
        float s = hsv[1];
        android.graphics.Color.colorToHSV(android.graphics.Color.GREEN, hsv);
        float coef = nAvailableUser/NmaxUsers;
        hsv[1] = s +  coef * (hsv[1] - s);
        return android.graphics.Color.HSVToColor(hsv);
    }
}
