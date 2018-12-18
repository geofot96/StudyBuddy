package ch.epfl.sweng.studdybuddy.core;

/**
 * Class representing a two friends
 */
public final class Buddy {

    private Pair buddies;

    public Buddy(){}

    /**
     * Construct buddies
     * @param aliceID the name of the first person id as a String
     * @param bobID the name of the second person id as a String
     */
    public Buddy(String aliceID, String bobID) {
        buddies = new Pair(aliceID, bobID);
    }

    //Returns null if not buddy, because no Optionnal for Java<8
    public String buddyOf(String uid) {
        if(getKey().equals(uid)) {
            return getValue();
        }
        else if(getValue().equals(uid)) {
            return getKey();
        }
        return null;
    }

    /*
     * Returns the first person id as a String
     */
    public String getKey() {
        return buddies.getKey();
    }

    /**
     * Sets the first person id to the one given as parameter
     * @param aliceID the new id
     */
    public void setKey(String aliceID) {
        buddies.setKey(aliceID);
    }

    /*
     * Returns the second person id as a String
     */
    public String getValue() {
        return buddies.getValue();
    }

    /**
     * Sets the second person id to the one given as parameter
     * @param bobID the new id
     */
    public void setValue(String bobID) {
        buddies.setValue(bobID);
    }

    /**
     * Returns the hash of the pair of buddies.
     * Note that this hash is symetric. An inverted pair of buddies will lead to the same hash
     * @return
     */
    public String hash() {
        return Integer.toHexString(getKey().hashCode() ^ getValue().hashCode());
    }
}
