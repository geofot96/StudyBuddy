package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import static ch.epfl.sweng.studdybuddy.DummyCourses.MATHEMATICS;

public class MainActivity extends AppCompatActivity
{
    //Temporary items, will be removed
    static DummyCourses dummy=new DummyCourses();
    User u1=new User("xxx@yyy.zzz","User One",MATHEMATICS,new ArrayList<Group>());
    Group g1=new Group(5,dummy.getListOfCourses().get(0),new ArrayList<User>());
    Group g2=new Group(7,dummy.getListOfCourses().get(4),new ArrayList<User>());
    static ArrayList<User> usersList1=new ArrayList<>();;
    static ArrayList<Group> groupList1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Temporary items, will be removed
        g1.addParticipant(u1);
        groupList1.add(g1);
        groupList1.add(g2);
        usersList1.add(u1);
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
