package ch.epfl.sweng.studdybuddy.activities;

import android.content.Context;

import android.content.Intent;

import android.support.annotation.NonNull; //TODO what is this?

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import ch.epfl.sweng.studdybuddy.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.CourseAdapter;
import ch.epfl.sweng.studdybuddy.CourseHolder;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.R;



public class CourseSelectActivity extends AppCompatActivity
{

    private static List<String> coursesDB;
    //List of selected courses
    private static final List<String> courseSelection = new ArrayList<>();


    private static AutoCompleteTextView autocomplete;

    private static Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);
        
        Intent other = getIntent();
        coursesDB = new ArrayList<>();
        coursesDB.add("Untitled");
        setUpButtons();
        ;
        setUpSelectedCourses();
        setUpDb(setUpAutoComplete());

    }

    private void setUpButtons() {

        final Intent toMain = new Intent(this, GroupsActivity.class);

        Button skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //
                //intent to main
                startActivity(toMain);
            }
        });


        doneButton = findViewById(R.id.doneButton);
        doneButton.setEnabled(false);
        doneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //tie courses to account
                //intent to main
                startActivity(toMain);
            }
        });
    }

    private ArrayAdapter<String> setUpAutoComplete(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);

        autocomplete = (AutoCompleteTextView) findViewById(R.id.courseComplete);
        autocomplete.setAdapter(adapter);

        autocomplete.setThreshold(0);
        autocomplete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AutoCompleteTextView textView = (AutoCompleteTextView)
                        findViewById(R.id.courseComplete);
                textView.showDropDown();
            }
        });
        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String textInput = parent.getAdapter().getItem(position).toString();
                if(!courseSelection.contains(textInput))
                    addCourse(textInput);
            }
        });
        autocomplete.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
            {
                //Check ENTER pressed
                if(event == null)
                {
                    if(actionId != EditorInfo.IME_ACTION_DONE && actionId != EditorInfo.IME_ACTION_NEXT)
                        return false;
                }
                else if(actionId == EditorInfo.IME_NULL)
                {
                    if(event.getAction() != KeyEvent.ACTION_DOWN) return true;
                }
                else return false;
                //Protection
                String textInput = autocomplete.getText().toString();
                if(Arrays.asList(coursesDB).contains(textInput) && !courseSelection.contains(textInput))
                    addCourse(textInput);
                return true;
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
       FirebaseReference firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
       firebase.select("courses").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, coursesDB, adapter));
   }

    private void addCourse(String course)
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