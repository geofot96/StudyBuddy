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
import ch.epfl.sweng.studdybuddy.UserCourseJoin;
import ch.epfl.sweng.studdybuddy.UserGroupJoin;

public class ProfileTab extends AppCompatActivity {

    private List<Course> usersCourses;
    private final List<Group> usersGroups = new ArrayList<>();

    FirebaseReference firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);
        usersCourses = new ArrayList<>();
        firebase = new FirebaseReference();
        StudyBuddy app = ((StudyBuddy) ProfileTab.this.getApplication());
        final String userId = app.getAuthendifiedUser().getUserID().toString();

        //usersCourses.addAll(((StudyBuddy) this.getApplication()).getAuthendifiedUser().getCoursesPreset());

        final RecyclerView recyclerView_courses = (RecyclerView) findViewById(R.id.courses_list);
        recyclerView_courses.setLayoutManager(new LinearLayoutManager(this));
        CourseAdapter courseAdapter = new  CourseAdapter(usersCourses);
        recyclerView_courses.setAdapter(courseAdapter);

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

        firebase.select("userCourse").getAll(UserCourseJoin.class, new Consumer<List<UserCourseJoin>>() {
            @Override
            public void accept(List<UserCourseJoin> pairs) {

                for(UserCourseJoin p : pairs) {
                    if (p.getUserID().toString().equals(userId)) {
                        firebase.select("courses").getAll(String.class, new Consumer<List<String>>() {
                            @Override
                            public void accept(List<String> courses) {
                                for (String c : courses) {
                                    if(c.equals(p.getCourseID().getId())) {
                                        usersCourses.add(new Course(c));
                                    }

                                    courseAdapter.notifyDataSetChanged();

                                }
                                if(usersCourses.size()==0){
                                    usersCourses.add(new Course("No courses"));
                                }
                            }
                        });
                    }
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
