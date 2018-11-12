package ch.epfl.sweng.studdybuddy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.util.CourseAdapter;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.util.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.util.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;
import ch.epfl.sweng.studdybuddy.core.User;

public class
ProfileTab extends AppCompatActivity {

//TODO delete
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);

    }


}
