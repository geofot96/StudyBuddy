package ch.epfl.sweng.studdybuddy.activities;

import android.content.Context;

import android.content.Intent;

import android.support.annotation.NonNull; //TODO what is this?

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.AuthManager;
import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.CourseAdapter;
import ch.epfl.sweng.studdybuddy.CourseHolder;
import ch.epfl.sweng.studdybuddy.FirebaseAuthManager;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.GroupsActivity;
import ch.epfl.sweng.studdybuddy.Pair;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.StudyBuddy;
import ch.epfl.sweng.studdybuddy.User;
import ch.epfl.sweng.studdybuddy.util.Helper;


public class CourseSelectActivity extends AppCompatActivity
{

    static List<String> coursesDB;
    //List of selected courses
    public static final List<Course> courseSelection = new ArrayList<>();


    static AutoCompleteTextView autocomplete;
    static ReferenceWrapper firebase;
    public static ArrayAdapter<String> adapter;
    static Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);

        Intent other = getIntent();
        coursesDB = new ArrayList<>();
        coursesDB.add("Untitled");

        setUpDb(setUpAutoComplete());
        setUpButtons();
        setUpSelectedCourses();

    }

    private void setUpButtons() {
        final Intent toMain = new Intent(this, GroupsActivity.class);
        Button skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                startActivity(toMain);
            }
        });
        doneButton = findViewById(R.id.doneButton);
        doneButton.setEnabled(false);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //tie courses to account
                //intent to main
                User currentUser = ((StudyBuddy) CourseSelectActivity.this.getApplication()).getAuthendifiedUser();
                AuthManager auth = new FirebaseAuthManager(CourseSelectActivity.this, getString(R.string.default_web_client_id));

                for(Course course : courseSelection){
                    Pair pair = new Pair(currentUser.getUserID().toString(), course.getCourseID().toString());
                    firebase.select("userCourse").select(Helper.hashCode(pair).toString()).setVal(pair);

                }
                //currentUser.setCoursesPreset(courseSelection);
                //Launches null pointer exception because the user is not fully initialized
                //currentUser.setCoursesPreset(courseSelection);
                startActivity(toMain);
            }
        });
    }

    private ArrayAdapter<String> setUpAutoComplete(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, coursesDB);
        autocomplete = (AutoCompleteTextView) findViewById(R.id.courseComplete);
        autocomplete.setAdapter(adapter);
        autocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView textView = (AutoCompleteTextView)
                        findViewById(R.id.courseComplete);
                textView.showDropDown();
            }
        });
        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textInput = parent.getAdapter().getItem(position).toString();
                if(!courseSelection.contains(textInput)) { addCourse(new Course(textInput)); }
            }
        });
        return adapter;
   }

   private void setUpSelectedCourses() {

       final RecyclerView selectedCourses = (RecyclerView) findViewById(R.id.coursesSet);
       selectedCourses.setLayoutManager(new LinearLayoutManager(this));

       selectedCourses.setAdapter(new CourseAdapter(courseSelection));
       ItemTouchHelper mIth = new ItemTouchHelper(
               new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                       ItemTouchHelper.RIGHT)
               {
                   @Override
                   public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1)
                   {
                       return false;
                   }

                   @Override
                   public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i)
                   {
                       CourseHolder cc = (CourseHolder) viewHolder;
                       courseSelection.remove(courseSelection.indexOf(cc.get()));
                       selectedCourses.getAdapter().notifyDataSetChanged();
                       if(courseSelection.size() == 0)
                           doneButton.setEnabled(false);
                   }
               });
       mIth.attachToRecyclerView(selectedCourses);
   }

   private void setUpDb(ArrayAdapter<String> adapter) {
       firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
       firebase.select("courses").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, coursesDB, new ArrayAdapterAdapter(adapter)));
   }

    private void addCourse(Course course)
    {
        courseSelection.add(course);
        //Dismiss KB
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autocomplete.getWindowToken(), 0);
        //reset search text
        autocomplete.setText("");
        doneButton.setEnabled(true);
    }

    private void removeCourse(String course)
    {
        courseSelection.remove(course);
    }

}
