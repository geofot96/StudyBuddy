package ch.epfl.sweng.studdybuddy.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ch.epfl.sweng.studdybuddy.Consumer;
import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.CourseAdapter;
import ch.epfl.sweng.studdybuddy.CourseHolder;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.StudyBuddy;
import ch.epfl.sweng.studdybuddy.UserGroupJoin;

public class ProfileTab extends AppCompatActivity {

    private List<String> usersCourses;
    private final List<Group> usersGroups = new ArrayList<>();

    FirebaseReference firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);
        usersCourses = new ArrayList<>();
        usersCourses.add("$no course");
    usersGroups.add(new Group(3, new Course("ok"), "fr"));
        firebase = new FirebaseReference();

        //usersCourses.addAll(((StudyBuddy) this.getApplication()).getAuthendifiedUser().getCoursesPreset());

        final RecyclerView recyclerView_courses = (RecyclerView) findViewById(R.id.courses_list);
        recyclerView_courses.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_courses.setAdapter(new CourseAdapter(usersCourses));

        final RecyclerView recyclerView_groups = (RecyclerView) findViewById(R.id.groups_list);
        recyclerView_groups.setLayoutManager(new LinearLayoutManager(this));
        GroupsRecyclerAdapter ad = new GroupsRecyclerAdapter(usersGroups);
        recyclerView_groups.setAdapter(ad);
        firebase.select("userGroup").getAll(UserGroupJoin.class, new Consumer<List<UserGroupJoin>>() {
            @Override
            public void accept(List<UserGroupJoin> join) {
                for(UserGroupJoin j: join){
                    Log.i("debug", j.getGroupID().getId());
                    firebase.select("groups").select(j.getGroupID().getId()).get(Group.class, new Consumer<Group>() {
                        @Override
                        public void accept(Group g) {
                            Log.i("debug", g.getCourse().getCourseName());
                            usersGroups.add(g);
ad.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        ItemTouchHelper mIth = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT){
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1){
                    return false;
                }
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i){
                    CourseHolder cc = (CourseHolder) viewHolder;
                    usersCourses.remove(usersCourses.indexOf(cc.get()));
                    recyclerView_courses.getAdapter().notifyDataSetChanged();
                }
            });
        mIth.attachToRecyclerView(recyclerView_courses);
    }
    private void removeCourse(String course){
        usersCourses.remove(course);
    }

}
