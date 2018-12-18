package ch.epfl.sweng.studdybuddy.core;

/**
 * Class representing  two friends
 */
public final class Buddy extends Pair{

    public Buddy(){}

    /**
     * Construct buddies
     * @param aliceID the name of the first person id as a String
     * @param bobID the name of the second person id as a String
     */
    public Buddy(String aliceID, String bobID) {
       super(aliceID, bobID);
    }

    /**
     * Returns the friend of a given userID
     * @param uid the user for which we search the friend
     * @return the id of the friend if the uid in parameter is correct, null otherwise
     */
    public String buddyOf(String uid) {
        if(getKey().equals(uid)) {
            return getValue();
        }
        else if(getValue().equals(uid)) {
            return getKey();
        }
        return null;
    }

    /**
     * Returns the hash of the pair of buddies.
     * Note that this hash is symetric. An inverted pair of buddies will lead to the same hash
     * @return the symetric hash
     */
    public String hash() {
        return Integer.toHexString(getKey().hashCode() ^ getValue().hashCode());
    }
}
