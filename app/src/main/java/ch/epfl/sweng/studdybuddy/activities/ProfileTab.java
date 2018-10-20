package ch.epfl.sweng.studdybuddy.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ch.epfl.sweng.studdybuddy.CourseAdapter;
import ch.epfl.sweng.studdybuddy.CourseHolder;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.StudyBuddy;

public class ProfileTab extends AppCompatActivity {

    private List<String> usersCourses;
    private final List<String> usersGroups = Arrays.asList("Linear Algebra", "Algorithms");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);
        usersCourses = new ArrayList<>();
        usersCourses.add("$no course");
        usersCourses.addAll(((StudyBuddy) this.getApplication()).getAuthendifiedUser().getCoursesPreset());

        final RecyclerView recyclerView_courses = (RecyclerView) findViewById(R.id.courses_list);
        recyclerView_courses.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_courses.setAdapter(new CourseAdapter(usersCourses));

        final RecyclerView recyclerView_groups = (RecyclerView) findViewById(R.id.groups_list);
        recyclerView_groups.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_groups.setAdapter(new CourseAdapter(usersGroups));

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
