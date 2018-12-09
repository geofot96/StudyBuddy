package ch.epfl.sweng.studdybuddy.core;

import java.util.Date;

import ch.epfl.sweng.studdybuddy.util.Helper;

public final class Buddy {
    public String getAlice() {
        return alice;
    }

    public void setAlice(String alice) {
        this.alice = alice;
    }

    public String getBob() {
        return bob;
    }

    public void setBob(String bob) {
        this.bob = bob;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    private String alice;
    private String bob;
    private Long creationDate;
    public Buddy(String alice, String bob) {
        creationDate = new Date().getTime();
        this.alice = alice;
        this.bob = bob;
    }

    //Returns null if not buddy, because no Optionnal for Java<8
    public String buddyOf(String uid) {
        String buddy = null;
        if(alice.equals(uid)) {
            buddy = bob;
        }
        else if(bob.equals(uid)) {
            buddy = alice;
        }
        return buddy;
    }

    public String hash() {
        return Helper.hashCode(new Pair(alice, bob));
    }
}
