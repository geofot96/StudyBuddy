package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DatabaseReference;

import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public final class MetaFactory {
    private MetaFactory() {}
    public static DatabaseReference deepFBReference() {
        return mock(DatabaseReference.class, Mockito.RETURNS_DEEP_STUBS);
    }
}
