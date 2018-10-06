package ch.epfl.sweng.studdybuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


public class CourseSelectActivity extends AppCompatActivity {

    private static final String coursesDB[]= new String[]{"Analysis", "Linear Algebra", "Algorithms", "Functionnal Programming",
    "Computer Language Processing", "Computer Networks"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);
        AutoCompleteTextView textView = (AutoCompleteTextView)
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
        /*textView.setOnClickListener((v) -> {
            AutoCompleteTextView tv = (AutoCompleteTextView)
                    findViewById(R.id.courseComplete);
            tv.showDropDown();
        });*/

    }

}
