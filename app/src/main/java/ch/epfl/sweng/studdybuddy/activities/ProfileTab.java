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
import ch.epfl.sweng.studdybuddy.User;
import ch.epfl.sweng.studdybuddy.UserCourseJoin;
import ch.epfl.sweng.studdybuddy.UserGroupJoin;

public class ProfileTab extends AppCompatActivity {

    private List<String> usersCourses = new ArrayList<>();
    private final List<Group> usersGroups = new ArrayList<>();
private RecyclerView recyclerView_groups;
private RecyclerView recyclerView_courses;
    FirebaseReference firebase;
    private GroupsRecyclerAdapter ad = new GroupsRecyclerAdapter(usersGroups);
    private CourseAdapter adCourse = new CourseAdapter(usersCourses);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);
        usersCourses.add("No courses");
        usersGroups.add(new Group(3, new Course("ok"), "fr"));
        firebase = new FirebaseReference();

        //usersCourses.addAll(.getCoursesPreset());

        recyclerView_courses = (RecyclerView) findViewById(R.id.courses_list);
        recyclerView_courses.setLayoutManager(new LinearLayoutManager(this));
        //setCoursesUp();

        recyclerView_groups = (RecyclerView) findViewById(R.id.groups_list);
        recyclerView_groups.setLayoutManager(new LinearLayoutManager(this));
        setGroupsUp();
    }
    private void removeCourse(String course){
        usersCourses.remove(course);
    }

    private void setGroupsUp() {

        recyclerView_groups.setAdapter(ad);
        firebase.select("userGroup").getAll(UserGroupJoin.class, new Consumer<List<UserGroupJoin>>() {
            @Override
            public void accept(List<UserGroupJoin> join) {
                User u = ((StudyBuddy) ProfileTab.this.getApplication()).getAuthendifiedUser();
                for(UserGroupJoin j: join){
                    if(j.getUserID().getId().equals(u.getUserID().getId())) {
                        firebase.select("groups").select(j.getGroupID().getId()).get(Group.class, new Consumer<Group>() {
                            @Override
                            public void accept(Group g) {
                                usersGroups.add(g);
                                ad.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    private void setCoursesUp() {
        recyclerView_courses.setAdapter(adCourse);
        firebase.select("userCourse").getAll(UserCourseJoin.class, new Consumer<List<UserCourseJoin>>() {
            @Override
            public void accept(List<UserCourseJoin> userCourseJoins) {
                User u = ((StudyBuddy) ProfileTab.this.getApplication()).getAuthendifiedUser();
                for(UserCourseJoin j: userCourseJoins) {
                    if(j.getUserID().getId().equals(u.getUserID().getId())) {
                        firebase.select("courses").select(j.getCourseID().getId()).get(String.class, new Consumer<String>() {
                            @Override
                            public void accept(String s) {
                                usersCourses.add(s);
                                adCourse.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });

    }

}
