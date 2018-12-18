package ch.epfl.sweng.studdybuddy.core;

import java.util.Date;

public final class Buddy {


    public String getKey() {
        return buddies.getKey();
    }

    public void setKey(String alice) {
        buddies.setKey(alice);
    }

    public String getValue() {
        return buddies.getValue();
    }

    public void setValue(String bob) {
        buddies.setValue(bob);
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    private Pair buddies;
    private Long creationDate;
    public Buddy(){}
    public Buddy(String alice, String bob) {
        creationDate = new Date().getTime();
        buddies = new Pair(alice, bob);
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

    public String hash() {
        return Integer.toHexString(getKey().hashCode() ^ getValue().hashCode());
    }
}
