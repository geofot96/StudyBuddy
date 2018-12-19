package ch.epfl.sweng.studdybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.tools.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.CourseAdapter;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.deleteCourseOnSwipe;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.onClickAddCourse;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.resetSelectViews;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.updateClickable;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.updateCoursesOnDone;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.onClickLaunch;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.showDropdown;

/**
 * Activity offering to the user the possibility to add a preferred course to his account
 */
public class CourseSelectActivity extends AppCompatActivity {
    static List<String> coursesDB = new ArrayList<>();
    private final List<String> courseSelection = new ArrayList<>();
    static AutoCompleteTextView autocomplete;
    static ReferenceWrapper firebase;
    public static ArrayAdapter<String> adapter;
    static Button doneButton;
    String uId;
    private MetaGroupAdmin mga;

    /**
     * OnCreate method setting up all the graphical components
     * @param savedInstanceState The state of the previous instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebase = new FirebaseReference();
        mga = new MetaGroupAdmin(firebase);
        setContentView(R.layout.activity_course_select);
        setUpButtons();
        setUpSelectedCourses();
        setUpDb(setUpAutoComplete());
    }
    /**
     * Method used to set up the graphical buttons
     */
    private void setUpButtons() {
        Intentable i = new Intentable(this, new Intent(this, NavigationActivity.class));
        Button skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(onClickLaunch(i));
        doneButton = findViewById(R.id.doneButton);
        User currentUser = ((StudyBuddy) CourseSelectActivity.this.getApplication()).getAuthendifiedUser();
        uId = currentUser.getUserID().getId();
        doneButton.setOnClickListener(updateCoursesOnDone(currentUser, courseSelection, mga, i));
    }
    /**
     * Prepares the text field which suggests courses as the user types
     * @return An ArrayAdapter of the suggested courses
     */
    private ArrayAdapter<String> setUpAutoComplete() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, coursesDB);
        autocomplete = (AutoCompleteTextView) findViewById(R.id.courseComplete);
        autocomplete.setAdapter(adapter);
        autocomplete.setOnClickListener(showDropdown(autocomplete));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        autocomplete.setOnItemClickListener(onClickAddCourse(courseSelection, resetSelectViews(doneButton, autocomplete, imm)));
        return adapter;
    }
    /**
     * Method which attaches to the suggested list only the appropriate courses
     */
    private void setUpSelectedCourses() {
        final RecyclerView selectedCourses = (RecyclerView) findViewById(R.id.coursesSet);
        RecyclerView.Adapter courseAdapter = new CourseAdapter(courseSelection);
        selectedCourses.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        selectedCourses.setAdapter(courseAdapter);
        AdapterAdapter adapterC = new RecyclerAdapterAdapter(courseAdapter);
        mga.addListenner(adapterC);
        mga.addListenner(updateClickable(doneButton, courseSelection));
        mga.getUserCourses(uId, courseSelection);
        ItemTouchHelper swipeCourse = deleteCourseOnSwipe(courseSelection, doneButton, adapterC);
        swipeCourse.attachToRecyclerView(selectedCourses);
    }
    /**
     * Selects the courses and stores them in a list
     * @param adapter Adapter containing the necessary information
     */
    private void setUpDb(ArrayAdapter<String> adapter) {
        firebase.select("courses").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, coursesDB, new ArrayAdapterAdapter(adapter)));
    }
}
