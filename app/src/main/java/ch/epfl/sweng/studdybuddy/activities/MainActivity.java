package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

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

    static ArrayList<User> usersList1 = new ArrayList<>();
    static ArrayList<Group> groupList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
