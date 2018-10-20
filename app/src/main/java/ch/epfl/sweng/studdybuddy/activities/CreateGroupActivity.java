package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.DummyMainActivity;
import ch.epfl.sweng.studdybuddy.GroupsActivity;
import ch.epfl.sweng.studdybuddy.DummyCourses;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.ID;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.User;
import ch.epfl.sweng.studdybuddy.UserGroupJoin;


public class CreateGroupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private String selectedCourse="";
    private String selectedLanguage;
    private int maxParticipants = 2;//default value
    private static List<String> coursesDB;

    private static final List<String> courseSelection = new ArrayList<>();
    private static AutoCompleteTextView textView;

    FirebaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent intent = getIntent();
        setUpLang();
        setUpNumberPicker();
        firebase = new FirebaseReference();
        coursesDB = new ArrayList<>();
        coursesDB.add("untitled");
        firebase.select("courses").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, coursesDB, new ArrayAdapterAdapter(setUpAutoComplete())));

    }

    ArrayAdapter<String> setUpAutoComplete() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);
        textView = (AutoCompleteTextView) findViewById(R.id.courseComplete2);
        textView.setAdapter(adapter);
        textView.setThreshold(0);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                AutoCompleteTextView textView = (AutoCompleteTextView)
                        findViewById(R.id.courseComplete2);
                textView.showDropDown();
            }
        });
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String textInput = parent.getItemAtPosition(position).toString();
                selectedCourse = textInput;
            }
        });
        return adapter;
    }

    void setUpNumberPicker() {
        //Number picker
        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(2);
        np.setMaxValue(10);
        np.setOnValueChangedListener(onValueChangeListener);
    }

    void setUpLang() {
        //Language spinner
        Spinner spinnerLanguage = (Spinner) findViewById(R.id.spinnerLanguage);
        spinnerLanguage.setOnItemSelectedListener(this);
        List<String> languagesList = Arrays.asList(DummyCourses.getListOfLanguages());
        ArrayAdapter<String> dataAdapterLanguages = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languagesList);
        dataAdapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(dataAdapterLanguages);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String item = parent.getItemAtPosition(position).toString();

        switch(parent.getId())
        {
            case R.id.spinnerLanguage:
                selectedLanguage = item;
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void addtoGroups(View view)
    {
        if(!selectedCourse.isEmpty() &&coursesDB.contains(selectedCourse)) {
					//Comunnicate through fb
            User test=new User("xx",new ID("MLHENpkefnewBDFHQNy58plb8VE3"),new ArrayList<>());//TODO use actual current user
            Group g = new Group(maxParticipants, new Course(selectedCourse),selectedLanguage, new ArrayList<User>(maxParticipants));
            firebase.select("groups").select(g.getGroupID()).setVal(g);
               UserGroupJoin pairUG=new UserGroupJoin(g.getGroupID().toString(), test.getUserID().toString());
            firebase.select("userGroup").select(pairUG.getId().toString()).setVal(pairUG);
            g.addParticipant(test);
            Intent intent = new Intent(this, GroupsActivity.class);
            startActivity(intent);
        }
        else {

            Toast.makeText(view.getContext(), "The course selected doesn't exist",
                    Toast.LENGTH_LONG).show();
        }
    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener()
    {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1)
        {

            maxParticipants = numberPicker.getValue();
        }
    };

    public static class GroupID {
    }
}
