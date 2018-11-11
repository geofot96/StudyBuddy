package ch.epfl.sweng.studdybuddy.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;

public class GroupInfoActivity extends AppCompatActivity{
    List<User> participants;
    MetaGroup mb;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        participants = new ArrayList<>();
        mb  = new MetaGroup();
    }
}
