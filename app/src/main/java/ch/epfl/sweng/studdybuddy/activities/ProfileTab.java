package ch.epfl.sweng.studdybuddy.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ch.epfl.sweng.studdybuddy.Consumer;
import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.CourseAdapter;
import ch.epfl.sweng.studdybuddy.CourseHolder;
import ch.epfl.sweng.studdybuddy.FirebaseAuthManager;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.GoogleSignInActivity;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.StudyBuddy;
import ch.epfl.sweng.studdybuddy.User;
import ch.epfl.sweng.studdybuddy.UserCourseJoin;
import ch.epfl.sweng.studdybuddy.UserGroupJoin;

public class ProfileTab extends AppCompatActivity {

    private final List<Course> userCourses =  new ArrayList<>();
    private  final List<Group> userGroups = new ArrayList<>();
    private RecyclerView recyclerView_groups;
    private RecyclerView recyclerView_courses;
    private FirebaseReference firebase;
    private GroupsRecyclerAdapter ad;
    private CourseAdapter adCourse;
    private User u;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);

        firebase = new FirebaseReference();
        u = ((StudyBuddy) ProfileTab.this.getApplication()).getAuthendifiedUser();
        userID = u.getUserID().toString();
        //usersCourses.addAll(.getCoursesPreset());


        adCourse = new CourseAdapter(userCourses);
        recyclerView_courses = (RecyclerView) findViewById(R.id.courses_list);
        recyclerView_courses.setLayoutManager(new LinearLayoutManager(this));
        setCoursesUp();

        ad = new GroupsRecyclerAdapter(userGroups);
        recyclerView_groups = (RecyclerView) findViewById(R.id.groups_list);
        recyclerView_groups.setLayoutManager(new LinearLayoutManager(this));
        setGroupsUp();
    }
    private void removeCourse(String course){
        userCourses.remove(course);
    }

    private void setGroupsUp() {

        recyclerView_groups.setAdapter(ad);
        firebase.select("userGroup").getAll(UserGroupJoin.class, new Consumer<List<UserGroupJoin>>() {
            @Override
            public void accept(List<UserGroupJoin> join) {
                User u = ((StudyBuddy) ProfileTab.this.getApplication()).getAuthendifiedUser();
                for(UserGroupJoin j: join){
                    if(j.getUserID().getId().equals(userID)) {
                        firebase.select("groups").select(j.getGroupID().getId()).get(Group.class, new Consumer<Group>() {
                            @Override
                            public void accept(Group g) {
                                userGroups.add(g);
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
            /*Right now courses are stored as a big array => we need to know the index, not the id which
            is the course name right for the moment

            @Override
            public void accept(List<UserCourseJoin> userCourseJoins) {
                userCourses.clear();
                for(UserCourseJoin j: userCourseJoins) {

                    /*if(j.getUserID().getId().equals(userID)) {
                        firebase.select("courses").select(j.getCourseID().getId()).get(String.class, new Consumer<String>() {
                            @Override
                            public void accept(String s) {
                                ///need to store courses as Course objects
                                userCourses.add(new Course(s));
                                adCourse.notifyDataSetChanged();
                            }
                        });
                    }
                     if(userCourses.size()==0){
                    userCourses.add(new Course("No courses"));
                }
                Log.i("userCourses size is", Integer.toString(userCourses.size()));
                adCourse.notifyDataSetChanged();
            }
        });
                }*/
            @Override
            public void accept(List<UserCourseJoin> pairs) {
                
                for(UserCourseJoin p : pairs) {
                    if (p.getUserID().toString().equals(userID)) {
                        firebase.select("courses").getAll(String.class, new Consumer<List<String>>() {
                            @Override
                            public void accept(List<String> courses) {
                                for (String c : courses) {
                                    if(c.equals(p.getCourseID().getId())) {
                                        userCourses.add(new Course(c));
                                    }
                                    adCourse.notifyDataSetChanged();
                                }
                                if(userCourses.size()==0){
                                    userCourses.add(new Course("No courses"));
                                }
                            }
                      });
                    }
                }
            }
        });

    }

}
