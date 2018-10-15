package ch.epfl.sweng.studdybuddy;

import com.google.firebase.database.DatabaseReference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

public class FirebaseReferenceTest {
    @Test
    public void getAllWithConsumerFillsParameterList() {
        DatabaseReference testRef = mock(DatabaseReference.class);
    }

    @Test
    public void getAllWithConsumer() {

    }

    @Test
    public void selectChildGoesToChild() {
        DatabaseReference testRef = mock(DatabaseReference.class);
        DatabaseReference childRef = mock(DatabaseReference.class);
        when(testRef.child("child")).thenReturn(childRef);
        FirebaseReference ref = new FirebaseReference(testRef);
        FirebaseReference res = (FirebaseReference) ref.select("child");
        Assert.assertThat(res.getRef(), is(childRef));
    }

    /*@Test//(expected = IndexOutOfBoundsException.class)
    public void selectOfNoChildBehaves() {
        DatabaseReference testRef = mock(DatabaseReference.class);
        DatabaseReference childRef = mock(DatabaseReference.class);
        when(testRef.child("child")).thenReturn(childRef);
        FirebaseReference ref = new FirebaseReference(testRef);
        FirebaseReference res = (FirebaseReference) ref.select("xyz");
    }*/
}
