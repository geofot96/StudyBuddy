package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * a ConcreteAvailability instance will modelize the availabilities
 * of the user in a list of Boolean matching the size of the calendar
 *
 */
public class ConcreteAvailability implements Availability{
    public final static int rowsNum = 11;
    public final static int columnsNum = 7;
    private int sizeOfTheCalendar = rowsNum*columnsNum;
    private List<Boolean> userAvailabilities = init();

    public ConcreteAvailability(){
    }

    /**
     * if the list is smaller than the one we expect, we are trying to pursue
     * the process by considering the missing slots as "false" i.e not available
     *
     * @param list
     */
    public ConcreteAvailability(List<Boolean> list){
        this.userAvailabilities = new ArrayList<>(list);
        int rest = list.size() > sizeOfTheCalendar ? 0: sizeOfTheCalendar - list.size();
        for(int i = 0; i<rest; i++){
            this.userAvailabilities.add(false);
        }
    }

    public List<Boolean> getUserAvailabilities() {
        return userAvailabilities;
    }

    public Boolean isAvailable(int row, int column) throws ArrayIndexOutOfBoundsException{
        if((row < 0) || (row >= rowsNum) || (column < 0) || (column >= columnsNum)){
            throw new ArrayIndexOutOfBoundsException("the availability slot doesn't exist");
        }
        return userAvailabilities.get(row * columnsNum + column);
    }

    public void modifyAvailability(int row, int column) throws ArrayIndexOutOfBoundsException{
        if((row < 0) || (row >= rowsNum) || (column < 0) || (column >= columnsNum)){
            throw new ArrayIndexOutOfBoundsException("the availability slot doesn't exist");
        } else {
            userAvailabilities.set(row*columnsNum+column, !isAvailable(row, column));
        }
    }


    private List<Boolean> init(){
        List<Boolean> x = new ArrayList<>(sizeOfTheCalendar);
        for(int i = 0; i<sizeOfTheCalendar; i++){
            x.add(false);
        }
        return x;
    }

}