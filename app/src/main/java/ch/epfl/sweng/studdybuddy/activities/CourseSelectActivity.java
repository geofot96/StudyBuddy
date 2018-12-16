package ch.epfl.sweng.studdybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.auth.AuthManager;
import ch.epfl.sweng.studdybuddy.auth.FirebaseAuthManager;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.tools.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.CourseAdapter;
import ch.epfl.sweng.studdybuddy.tools.Holder;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.showDropdown;

/**
 * An activity used by the user to create a new group with specific course, language and participant number limit
 */
public class CourseSelectActivity extends AppCompatActivity {

    static List<String> coursesDB;
    //List of selected courses
    public static final List<String> courseSelection = new ArrayList<>();
    static AutoCompleteTextView autocomplete;
    static ReferenceWrapper firebase;
    public static ArrayAdapter<String> adapter;
    static Button doneButton;

    /**
     * OnCreate method setting up all the graphical components
     * @param savedInstanceState The state of the previous instance
     */
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

    /**
     * Method used to set up the graphical buttons
     */
    private void setUpButtons() {
        final Intent toMain = new Intent(this, NavigationActivity.class);
        Button skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toMain);
            }
        });
        doneButton = findViewById(R.id.doneButton);
        doneButton.setEnabled(false);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = ((StudyBuddy) CourseSelectActivity.this.getApplication()).getAuthendifiedUser();
                AuthManager auth = new FirebaseAuthManager(CourseSelectActivity.this, getString(R.string.default_web_client_id));
                for (String course : courseSelection) {
                    Pair pair = new Pair(currentUser.getUserID().toString(), course);
                    firebase.select("userCourse").select(Helper.hashCode(pair).toString()).setVal(pair);
                }

                startActivity(toMain);
            }
        });
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
        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textInput = parent.getAdapter().getItem(position).toString();
                if (!courseSelection.contains(textInput)) {
                    addCourse(textInput);
                }
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
                        ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                        Holder cc = (Holder) viewHolder;
                        courseSelection.remove(courseSelection.indexOf(cc.get()));
                        selectedCourses.getAdapter().notifyDataSetChanged();
                        if (courseSelection.size() == 0)
                            doneButton.setEnabled(false);
                    }
                });
        mIth.attachToRecyclerView(selectedCourses);
    }

    private void setUpDb(ArrayAdapter<String> adapter) {
        firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
        firebase.select("courses").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, coursesDB, new ArrayAdapterAdapter(adapter)));
    }

    private void addCourse(String course) {
        courseSelection.add(course);
        //Dismiss KB
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(autocomplete.getWindowToken(), 0);
        //reset search text
        autocomplete.setText("");
        doneButton.setEnabled(true);
    }

    private void removeCourse(String course) {
        courseSelection.remove(course);
    }

}
