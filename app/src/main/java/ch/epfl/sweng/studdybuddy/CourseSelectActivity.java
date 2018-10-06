package ch.epfl.sweng.studdybuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CourseSelectActivity extends AppCompatActivity {

    private static final String coursesDB[]= new String[]{"Analysis", "Linear Algebra", "Algorithms", "Functionnal Programming",
    "Computer Language Processing", "Computer Networks"};

    private static final List<String> courseSelection = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);
        final AutoCompleteTextView textView = (AutoCompleteTextView)
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

        //textView.onKeyUp(KeyEvent.KEYCODE_ENTER, new Key)
    }

}
