package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.DummyCourses;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.FirebaseWrapper;

import ch.epfl.sweng.studdybuddy.Friendship;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.ID;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.User;

import static ch.epfl.sweng.studdybuddy.DummyCourses.MATHEMATICS;

public class MainActivity extends AppCompatActivity
{
    //TODO Temporary items, will be removed
    static DummyCourses dummy = new DummyCourses();
    User u1 = new User("xxx@yyy.zzz", "User 1", MATHEMATICS, new ArrayList<Group>(),new ArrayList<User>());
    Group g1 = new Group(5, new Course(DummyCourses.getListOfCourses()[0]),DummyCourses.getListOfLanguages()[0], new ArrayList<User>());
    Group g2 = new Group(7, new Course(DummyCourses.getListOfCourses()[3]),DummyCourses.getListOfLanguages()[2], new ArrayList<User>());

    public static ArrayList<User> usersList1 = new ArrayList<>();
    public static ArrayList<Group> groupList1 = new ArrayList<>(); //made it public for create group which complained

    public String p(Object o, int i){
        return ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments()[i].getTypeName();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseWrapper firebase = new FirebaseWrapper(new FirebaseReference());

        firebase.putGroup(new Group(10, new Course("Computer Langage Processing"), "fr", new ArrayList<>()));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
firebase.getAllGroups();
        /*ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("TAG",dataSnapshot.child("groups").getChildren().iterator().next().getValue().getClass().toString());
                Log.i("TAG",p(dataSnapshot.child("groups").getChildren().iterator().next().getValue().getClass(),0));
                Log.i("TAG",p(dataSnapshot.child("groups").getChildren().iterator().next().getValue().getClass(),1));

                Collection k = ((HashMap)(dataSnapshot.child("groups").getChildren().iterator().next().getValue())).keySet();
                Collection v = ((HashMap)(dataSnapshot.child("groups").getChildren().iterator().next().getValue())).values();

                k.forEach(x -> Log.i("TAG", x.getClass().toString()));
                v.forEach(x -> Log.i("TAG", x.getClass().toString()));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });*/
        //mDatabase.child("tests").setValue("test1");
        if(groupList1.isEmpty())
        {//TODO Temporary items, will be removed after the demo
            g1.addParticipant(u1);
            groupList1.add(g1);
            groupList1.add(g2);
            usersList1.add(u1);
        }
    }


    public void gotoGroups(View view)
    {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

    public void gotoFred(View view)
    {
        Intent intent = new Intent(this, CourseSelectActivity.class);
        startActivity(intent);
    }


}
