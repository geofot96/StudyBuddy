package ch.epfl.sweng.studdybuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
        textView = (AutoCompleteTextView)
                findViewById(R.id.courseComplete);
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
        courseSelection.add("Wine tasting");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.coursesSet);

        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //pour adapter en grille comme une RecyclerView, avec 2 cellules par ligne
        //recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //puis créer un MyAdapter, lui fournir notre liste de villes.
        //cet adapter servira à remplir notre recyclerview
        recyclerView.setAdapter(new CourseAdapter(courseSelection));
        /*textView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //textView.performCompletion();

                    courseSelection.add(textView.getText().toString());
                    return true;
                }
                return false;
            }
        });*/
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (event==null) {
                    if (actionId==EditorInfo.IME_ACTION_DONE);
                        // Capture soft enters in a singleLine EditText that is the last EditText.
                    else if (actionId==EditorInfo.IME_ACTION_NEXT);
                        // Capture soft enters in other singleLine EditTexts
                    else return false;  // Let system handle all other null KeyEvents
                }
                else if (actionId==EditorInfo.IME_NULL) {
                    // Capture most soft enters in multi-line EditTexts and all hard enters.
                    // They supply a zero actionId and a valid KeyEvent rather than
                    // a non-zero actionId and a null event like the previous cases.
                    if (event.getAction()==KeyEvent.ACTION_DOWN);
                        // We capture the event when key is first pressed.
                    else  return true;   // We consume the event when the key is released.
                }
                else  return false;
                // We let the system handle it when the listener
                // is triggered by something that wasn't an enter.


                // Code from this point on will execute whenever the user
                // presses enter in an attached view, regardless of position,
                // keyboard, or singleLine status.
                courseSelection.add(textView.getText().toString());
                return true;   // Consume the event
            }
        });
        //textView.setImeActionLabel("", KeyEvent.KEYCODE_ENTER);
    }

}

/*
* new TextView.OnEditorActionListener() {
  @Override
  public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
    if (event==null) {
      if (actionId==EditorInfo.IME_ACTION_DONE);
      // Capture soft enters in a singleLine EditText that is the last EditText.
      else if (actionId==EditorInfo.IME_ACTION_NEXT);
      // Capture soft enters in other singleLine EditTexts
      else return false;  // Let system handle all other null KeyEvents
    }
    else if (actionId==EditorInfo.IME_NULL) {
    // Capture most soft enters in multi-line EditTexts and all hard enters.
    // They supply a zero actionId and a valid KeyEvent rather than
    // a non-zero actionId and a null event like the previous cases.
      if (event.getAction()==KeyEvent.ACTION_DOWN);
      // We capture the event when key is first pressed.
      else  return true;   // We consume the event when the key is released.
    }
    else  return false;
    // We let the system handle it when the listener
    // is triggered by something that wasn't an enter.


    // Code from this point on will execute whenever the user
    // presses enter in an attached view, regardless of position,
    // keyboard, or singleLine status.

    if (view==multiLineEditText)  multiLineEditText.setText("You pressed enter");
    if (view==singleLineEditText)  singleLineEditText.setText("You pressed next");
    if (view==lastSingleLineEditText)  lastSingleLineEditText.setText("You pressed done");
    return true;   // Consume the event
  }
};*/