package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.DummyCourses;
import ch.epfl.sweng.studdybuddy.Group;
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

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // My top posts by number of stars
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Info", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        System.out.println(mDatabase.child("courses"));
        mDatabase.child("tests").setValue("test1");
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
