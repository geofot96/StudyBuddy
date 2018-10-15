package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DatabaseReference;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class FirebaseReferenceTest {
    @Test
    public void getAllWithConsumerFillsParameterList() {

    }

    @Test
    public void getAllWithConsumer() {
        DatabaseReference testRef = mock(DatabaseReference.class);
        DatabaseReference childRef = mock(DatabaseReference.class);
        when(testRef.child("child")).thenReturn(childRef);
    }

    @Test
    public void selectChildGoesToChild() {

    }

    @Test
    public void selectOfNoChildBehaves() {

    }
}
