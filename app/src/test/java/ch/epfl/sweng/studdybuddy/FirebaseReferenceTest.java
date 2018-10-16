package ch.epfl.sweng.studdybuddy;

import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseReferenceTest {

    //@Mock
    //private DatabaseReference databaseReference;


    @Test
    public void getAllWithConsumerFillsParameterList() {
        DatabaseReference testref = mock(DatabaseReference.class);
        DataSnapshot dataSnapshot = mock(DataSnapshot.class);
        //when(testref.setValue("key")).th;

        Group emptyGroup = new Group(3, new Course("SDP"), "fr", new ArrayList<User>());
        when(dataSnapshot.getValue(Group.class)).thenReturn(emptyGroup);
        when(dataSnapshot.getChildren()).thenReturn(Arrays.asList(dataSnapshot));
        ArgumentCaptor<ValueEventListener> argument = ArgumentCaptor.forClass(ValueEventListener.class);

        //verify(testref).addListenerForSingleValueEvent(argument.capture());
        //argument.getValue().onDataChange(dataSnapshot);
        FirebaseReference ref  =new FirebaseReference(testref);
        ref.getAllMock(Group.class, new Consumer<List<Group>>() {
            @Override
            public void accept(List<Group> groups) {
                Group g = groups.get(0);

                assertThat(g.getMaxNoUsers(), is(3));
                assertThat(g.getLang(),  is("fr"));
                //...
            }
        }).onDataChange(dataSnapshot);
        //


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




    @Test
    public void getReturnsData(){
        DatabaseReference db = mock(DatabaseReference.class);
        FirebaseReference fb = new FirebaseReference(db);

        DataSnapshot ds = mock(DataSnapshot.class);
        final Group clp = new Group(10,new Course("CLP"), "EN", new ArrayList<User>());


        when(ds.getValue(Group.class)).thenReturn(clp);
         Group[] box = new Group[1];

       fb.getMock(Group.class, new Consumer<Group>() {
           @Override
           public void accept(Group group) {
               assertTrue(group.toString().equals(clp.toString()));
           }
       }).onDataChange(ds);

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