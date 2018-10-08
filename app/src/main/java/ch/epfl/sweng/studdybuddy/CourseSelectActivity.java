package ch.epfl.sweng.studdybuddy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CourseSelectActivity extends AppCompatActivity {

    private static final String coursesDB[]= new String[]{"Analysis", "Linear Algebra", "Algorithms", "Functionnal Programming",
    "Computer Language Processing", "Computer Networks"};

    private static final List<String> courseSelection = new ArrayList<>();

    private static AutoCompleteTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);
        textView = (AutoCompleteTextView)findViewById(R.id.courseComplete);
        textView.setAdapter(adapter);
        textView.setThreshold(0);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView textView = (AutoCompleteTextView)
                        findViewById(R.id.courseComplete);
                textView.showDropDown();
            }
        });
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textInput = parent.getItemAtPosition(position).toString();
                if(!courseSelection.contains(textInput))
                    addCourse(textInput);
            }
        });
        //courseSelection.add("Wine tasting");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.coursesSet);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new CourseAdapter(courseSelection));
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (event==null) {
                    if (actionId!=EditorInfo.IME_ACTION_DONE && actionId!=EditorInfo.IME_ACTION_NEXT)
                        return false;
                }
                else if (actionId==EditorInfo.IME_NULL) {
                    if (event.getAction()!=KeyEvent.ACTION_DOWN) return true;
                }
                else  return false;
                //Unnecessary
                textView.performCompletion();
                textView.performValidation();
                //Protection
                String textInput = textView.getText().toString();
                if(Arrays.asList(coursesDB).contains(textInput) && !courseSelection.contains(textInput)) addCourse(textInput);
                return true;
            }
        });
        Button skipButton = (Button)findViewById(R.id.skipButton);
        Button doneButton = (Button)findViewById(R.id.doneButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //intent to main
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tie courses to account
                //intent to main
            }
        });
    }

    private void addCourse(String course) {
        courseSelection.add(course);
        //Dismiss KB
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
        //reset search text
        textView.setText("");
    }



}