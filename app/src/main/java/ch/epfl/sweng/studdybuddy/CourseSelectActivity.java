package ch.epfl.sweng.studdybuddy;

import android.content.Context;

import android.content.Intent;

import android.support.annotation.NonNull; //TODO what is this?

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CourseSelectActivity extends AppCompatActivity
{

    private static final String coursesDB[] = new String[]{"Analysis", "Linear Algebra", "Algorithms", "Functionnal Programming",
            "Computer Language Processing", "Computer Networks"};

    private static final List<String> courseSelection = new ArrayList<>();

    private static AutoCompleteTextView textView;

    private static Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_select);
        
        Intent other = getIntent();

        Intent toMain = new Intent(this, GroupsActivity.class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);
        Button skipButton = findViewById(R.id.skipButton);
        doneButton = findViewById(R.id.doneButton);
        doneButton.setEnabled(false);
        textView = (AutoCompleteTextView) findViewById(R.id.courseComplete);
        initTextView(textView, adapter);
        //courseSelection.add("Wine tasting");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.coursesSet);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new CourseAdapter(courseSelection));
        ItemTouchHelper mIth = itemTouchHelper(recyclerView, doneButton);
        mIth.attachToRecyclerView(recyclerView);


        buttonListenerIntent(skipButton, toMain);

        buttonListenerIntent(doneButton, toMain);
    }
    private ItemTouchHelper itemTouchHelper(RecyclerView recyclerView, Button button){
        return new ItemTouchHelper(
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
                        recyclerView.getAdapter().notifyDataSetChanged();
                        if(courseSelection.size() == 0)
                            button.setEnabled(false);
                    }
                });
    }
    private void initTextView(AutoCompleteTextView textView, ArrayAdapter<String>  adapter){
        textView.setAdapter(adapter);
        textView.setThreshold(0);
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AutoCompleteTextView textView = (AutoCompleteTextView)
                        findViewById(R.id.courseComplete);
                textView.showDropDown();
            }
        });
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String textInput = parent.getAdapter().getItem(position).toString();
                if(!courseSelection.contains(textInput))
                    addCourse(textInput);
            }
        });

        textView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
            {
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
                String textInput = textView.getText().toString();
                if(Arrays.asList(coursesDB).contains(textInput) && !courseSelection.contains(textInput))
                    addCourse(textInput);
                return true;
            }
        });
    }
    private void buttonListenerIntent(Button button, Intent target){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(target);
            }
        });
    }
    private void addCourse(String course)
    {
        courseSelection.add(course);
        //Dismiss KB
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
        //reset search text
        textView.setText("");
        doneButton.setEnabled(true);
    }

    private void removeCourse(String course)
    {
        courseSelection.remove(course);
    }

}