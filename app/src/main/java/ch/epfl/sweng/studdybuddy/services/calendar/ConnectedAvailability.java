package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.List;

import ch.epfl.sweng.studdybuddy.FirebaseReference;

public class ConnectedAvailability implements Availability {
    private Availability A;
    private FirebaseReference ref;
    private String userID;
    private String groupID;

    public ConnectedAvailability(String user, String group){
        this(user, group, new ConcreteAvailability());
    }

    public ConnectedAvailability(String user, String group, Availability A){
        this(user, group, A, new FirebaseReference());
    }

    public ConnectedAvailability(String user, String group, Availability A, FirebaseReference ref){
        this.A = A;
        this.ref = ref;
        this.userID = user;
        this.groupID = group;
        update();
    }

    @Override
    public void addAvailability(int row, int column) throws ArrayIndexOutOfBoundsException {
        try{
            A.addAvailability(row, column);
            update();
        }catch (ArrayIndexOutOfBoundsException e){
            throw e;
        }
    }

    @Override
    public void removeAvailability(int row, int column) throws ArrayIndexOutOfBoundsException {
        try{
            A.removeAvailability(row, column);
            update();
        }catch(ArrayIndexOutOfBoundsException e){
            throw e;
        }
    }

    @Override
    public List<Boolean> getUserAvailabilities() {
        return A.getUserAvailabilities();
    }

    public Boolean isAvailable(int row, int column){
        return A.isAvailable(row, column);
    }

    private void update(){
        ref.select("availabilities").select(groupID).select(userID).setVal(A.getUserAvailabilities());
    }
}
