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
import ch.epfl.sweng.studdybuddy.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.StudyBuddy;
import ch.epfl.sweng.studdybuddy.User;

public class
ProfileTab extends AppCompatActivity {

    private final List<Course> userCourses =  new ArrayList<>();
    private  final List<Group> userGroups = new ArrayList<>();
    private RecyclerView recyclerView_groups;
    private RecyclerView recyclerView_courses;
    protected ReferenceWrapper firebase;
    private GroupsRecyclerAdapter ad;
    private CourseAdapter adCourse;
    private User user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);

       //
        firebase = getDB();
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

    public void setGroupsUp() {
        firebase.select("userGroup").getAll(Pair.class, userGroupConsumer());
    }

    public Consumer<List<Pair>> userGroupConsumer(){
        return new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                if(pairs != null){
                    for(Pair pair: pairs){
                        if(pair.getKey().equals(userID)) {
                            firebase.select("groups").select(pair.getValue()).get(Group.class, groupConsumer());
                        }
                    }
                }
            }
        };
    }

    public Consumer<Group> groupConsumer(){
        return new Consumer<Group>() {
            @Override
            public void accept(Group g) {
                userGroups.add(g);
                ad.notifyDataSetChanged();
            }
        };
    }


    public void setCoursesUp() {
        firebase.select("userCourse").getAll(Pair.class, userCourseConsumer());
    }


    public Consumer<List<Pair>> userCourseConsumer(){
        return new Consumer<List<Pair>>() {
            @Override
            public void accept(List<Pair> pairs) {
                if (pairs != null) {

                    aggregate(pairs);
                }
            }
        };
    }

    private void aggregate(List<Pair> pairs) {
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
                    }
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

    public ReferenceWrapper getDB(){
        return new FirebaseReference();
    }

    public void setDB(ReferenceWrapper r){ this.firebase = r; }

}
