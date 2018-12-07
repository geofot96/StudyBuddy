package ch.epfl.sweng.studdybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.auth.AuthManager;
import ch.epfl.sweng.studdybuddy.auth.FirebaseAuthManager;
import ch.epfl.sweng.studdybuddy.controllers.CourseSelectController;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.Adapter;
import ch.epfl.sweng.studdybuddy.tools.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.tools.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.CourseAdapter;
import ch.epfl.sweng.studdybuddy.tools.Holder;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.updateCoursesOnDone;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.onClickLaunch;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.showDropdown;


public class CourseSelectActivity extends AppCompatActivity
{

    static List<String> coursesDB = new ArrayList<>();
    //List of selected courses
    public static final List<String> courseSelection = new ArrayList<>();


    static AutoCompleteTextView autocomplete;
    static ReferenceWrapper firebase;
    public static ArrayAdapter<String> adapter;
    static Button doneButton;
    String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);
        setUpDb(setUpAutoComplete());
        setUpButtons();
        setUpSelectedCourses();
    }

    private void setUpButtons() {
        final Intent toMain = new Intent(this, NavigationActivity.class);
        Button skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(onClickLaunch(new Intentable(this ,toMain)));
        doneButton = findViewById(R.id.doneButton);
        doneButton.setEnabled(false);
        Intentable i = new Intentable(this, toMain);
        User currentUser = ((StudyBuddy) CourseSelectActivity.this.getApplication()).getAuthendifiedUser();
        uId = currentUser.getUserID().getId();
        doneButton.setOnClickListener(updateCoursesOnDone(currentUser, courseSelection, new MetaGroupAdmin(), i));
    }

    private ArrayAdapter<String> setUpAutoComplete(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, coursesDB);
        autocomplete = (AutoCompleteTextView) findViewById(R.id.courseComplete);
        autocomplete.setAdapter(adapter);
        autocomplete.setOnClickListener(showDropdown(autocomplete));
        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textInput = parent.getAdapter().getItem(position).toString();
                if(!courseSelection.contains(textInput)) { addCourse(textInput); }
            }
        });
        return adapter;
   }

   private void setUpSelectedCourses() {
       final RecyclerView selectedCourses = (RecyclerView) findViewById(R.id.coursesSet);
       Adapter courseAdapter = new CourseAdapter(courseSelection);
       selectedCourses.setAdapter(new CourseAdapter(courseSelection));
       //Log.i("User ID", uId);
       //new MetaGroup(new RecyclerAdapterAdapter(courseAdapter)).getUserCourses(uId, courseSelection);
       /*ItemTouchHelper mIth =
       mIth.attachToRecyclerView(selectedCourses);*/
   }

   private void setUpDb(ArrayAdapter<String> adapter) {
       firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
       firebase.select("courses").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, coursesDB, new ArrayAdapterAdapter(adapter)));
   }

    private void addCourse(String course)
    {
        courseSelection.add(course);
        dismissKB();
        //reset search text
        autocomplete.setText("");
        doneButton.setEnabled(true);
    }

    private void dismissKB() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autocomplete.getWindowToken(), 0);
    }
}
