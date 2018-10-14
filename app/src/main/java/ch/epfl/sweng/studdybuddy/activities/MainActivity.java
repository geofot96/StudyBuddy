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
import ch.epfl.sweng.studdybuddy.DatabaseWrapper;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
