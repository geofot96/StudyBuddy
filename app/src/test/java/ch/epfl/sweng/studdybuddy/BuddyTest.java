package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import ch.epfl.sweng.studdybuddy.core.Buddy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BuddyTest {
    @Test
    public void testSettersGetters() {
        Buddy b = new Buddy("alice", "bob");
        b.setKey("bob");
        b.setValue("alice");
        assertEquals("alice", b.getValue());
        assertEquals("bob", b.getKey());
    }

    @Test
    public void testBuddyOf() {
        Buddy b = new Buddy("alice", "bob");
        assertEquals("bob", b.buddyOf("alice"));
        assertEquals("alice", b.buddyOf("bob"));
        assertNull(b.buddyOf("eve"));
    }
    @Test
    public void testHash() {
        Buddy b = new Buddy("alice", "bob");
        Buddy b2 = new Buddy("bob", "alice");
        assertEquals(Integer.toHexString("alice".hashCode() ^ "bob".hashCode()), b.hash());
        assertEquals(b.hash(), b2.hash());
    }
}
