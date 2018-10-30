package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.ArrayList;
import java.util.List;

public class ConcreteAvailability implements Availability{
    public final static int rowsNum = 7;
    public final static int columnsNum = 11;
    private int size = rowsNum*columnsNum;
    private List<Boolean> userAvailabilities = init();

    public ConcreteAvailability(){
    }

    public ConcreteAvailability(List list){
        this.userAvailabilities = list;
        int rest = list.size() > size ? 0: size - list.size();
        for(int i = 0; i<rest; i++){
            this.userAvailabilities.add(false);
        }
    }

    public void addAvailability(int row, int column) throws ArrayIndexOutOfBoundsException{
        modifyAvailability(row,column, true);
    }

    public void removeAvailability(int row, int column) throws ArrayIndexOutOfBoundsException{
        modifyAvailability(row, column, false);
    }

    public List<Boolean> getUserAvailabilities() {
        return userAvailabilities;
    }

    public Boolean isAvailable(int row, int column) {
        return userAvailabilities.get(row * columnsNum + column);
    }

    private List<Boolean> init(){
        List<Boolean> x = new ArrayList<>(size);
        for(int i = 0; i<size; i++){
            x.add(false);
        }
        return x;
    }

    private void modifyAvailability(int row, int column, Boolean bool) throws ArrayIndexOutOfBoundsException{
        if((row < 0) || (row >= rowsNum) || (column < 0) || (column >= columnsNum)){
            throw new ArrayIndexOutOfBoundsException("the availability slot doesn't exist");
        } else {
            userAvailabilities.set(row*columnsNum+column, bool);
        }
    }
}