package ch.epfl.sweng.studdybuddy.services.calendar;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;

/**
 * The ConnectedAvailability is linked to an instance of Availability and will
 * update the database every time the availabilities are updated
 */
public class ConnectedAvailability implements Availability {
    private Availability A;
    private ReferenceWrapper ref;
    private String userID;
    private String groupID;

    public ConnectedAvailability(String user, String group){
        this(user, group, new ConcreteAvailability());
    }

    public ConnectedAvailability(String user, String group, Availability A){
        this(user, group, A, new FirebaseReference());
    }

    public ConnectedAvailability(String user, String group, Availability A, ReferenceWrapper ref){
        this.A = A;
        this.ref = ref;
        this.userID = user;
        this.groupID = group;
        update();
    }


    @Override
    public List<Boolean> getUserAvailabilities() {
        return A.getUserAvailabilities();
    }

    public Boolean isAvailable(int row, int column){
        return A.isAvailable(row, column);
    }

    @Override
    public void modifyAvailability(int row, int column) throws ArrayIndexOutOfBoundsException {
        A.modifyAvailability(row, column);
        update();
    }

    private void update(){
        ref.select("availabilities").select(groupID).select(userID).setVal(A.getUserAvailabilities());
    }

    public static void removeAvailabiliity(ID<Group> group, ID<User> user, ReferenceWrapper ref){
        ref.select("availabilities").select(group.getId()).select(user.getId()).clear();
    }
}
