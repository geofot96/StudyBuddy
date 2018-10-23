package ch.epfl.sweng.studdybuddy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.Consumer;
import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.CourseAdapter;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.GroupsRecyclerAdapter;
import ch.epfl.sweng.studdybuddy.Pair;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.StudyBuddy;
import ch.epfl.sweng.studdybuddy.User;

public class ProfileTab extends AppCompatActivity {

    private final List<Course> userCourses =  new ArrayList<>();
    private  final List<Group> userGroups = new ArrayList<>();
    private RecyclerView recyclerView_groups;
    private RecyclerView recyclerView_courses;
    private FirebaseReference firebase;
    private GroupsRecyclerAdapter ad;
    private CourseAdapter adCourse;
    private User user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);

        firebase = new FirebaseReference();
        user = ((StudyBuddy) ProfileTab.this.getApplication()).getAuthendifiedUser();
        userID = user.getUserID().toString();
        //usersCourses.addAll(.getCoursesPreset());
        setUI();
        setCoursesUp();
        setGroupsUp();
    }
    private void removeCourse(String course){
        userCourses.remove(course);
    }

    private void setGroupsUp() {

        firebase.select("userGroup").getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                for(Pair pair: pairs){
                    if(pair.getKey().equals(userID)) {
                        firebase.select("groups").select(pair.getValue()).get(Group.class, new Consumer<Group>() {
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
        firebase.select("userCourse").getAll(Pair.class, new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                if (pairs != null) {
                    firebase.select("courses").getAll(String.class, new Consumer<List<String>>() {
                        @Override
                        public void accept(List<String> courses) {
                            for (Pair pair : pairs) {
                                if (pair.getKey().toString().equals(userID)) {
                                    for (String course : courses) {
                                        if (course.equals(pair.getValue())) {
                                            userCourses.add(new Course(course));
                                        }
                                        adCourse.notifyDataSetChanged();
                                    }
                                    if (userCourses.size() == 0) {
                                        userCourses.add(new Course("No courses"));
                                    }
                                }
                            }
                        }
                    });

                }
            }
        });

    }

    private void setUI(){
        TextView nameView = (TextView) findViewById(R.id.profile_name_text);
        nameView.setText(user.getName());

        adCourse = new CourseAdapter(userCourses);
        recyclerView_courses = (RecyclerView) findViewById(R.id.courses_list);
        recyclerView_courses.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_courses.setAdapter(adCourse);

        ad = new GroupsRecyclerAdapter(userGroups);
        recyclerView_groups = (RecyclerView) findViewById(R.id.groups_list);
        recyclerView_groups.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_groups.setAdapter(ad);
    }

}
