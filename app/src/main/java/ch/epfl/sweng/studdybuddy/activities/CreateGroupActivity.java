package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.Course;
import ch.epfl.sweng.studdybuddy.GroupsActivity;
import ch.epfl.sweng.studdybuddy.DummyCourses;
import ch.epfl.sweng.studdybuddy.FirebaseReference;
import ch.epfl.sweng.studdybuddy.Group;
import ch.epfl.sweng.studdybuddy.MetaGroup;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.StudyBuddy;
import ch.epfl.sweng.studdybuddy.User;

import static ch.epfl.sweng.studdybuddy.ActivityHelper.showDropdown;
public class CreateGroupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private String selectedCourse="";
    private String selectedLanguage;
    private int maxParticipants = 2;//default value
    private static List<String> coursesDB;

    private static final List<String> courseSelection = new ArrayList<>();
    private static AutoCompleteTextView textView;

    FirebaseReference firebase;
    MetaGroup mb;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        mb = new MetaGroup();
        Intent intent = getIntent();
        setUpLang();
        setUpNumberPicker();
        firebase = new FirebaseReference();
        coursesDB = new ArrayList<>();
        coursesDB.add("untitled");
        firebase.select("courses").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, coursesDB, new ArrayAdapterAdapter(setUpAutoComplete())));
        create = (Button)findViewById(R.id.confirmGroupCreation);
        create.setEnabled(false);
    }

    ArrayAdapter<String> setUpAutoComplete() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);
        textView = (AutoCompleteTextView) findViewById(R.id.courseComplete2);
        textView.setAdapter(adapter);
        textView.setOnClickListener(showDropdown(textView));
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String textInput = parent.getItemAtPosition(position).toString();
                selectedCourse = textInput;
                create.setEnabled(true);
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
            Group g = new Group(maxParticipants, new Course(selectedCourse),selectedLanguage);

            User user = ((StudyBuddy) CreateGroupActivity.this.getApplication()).authendifiedUser;
            mb.pushGroup(g, user.getUserID().getId());
		        Intent intent = new Intent(this, GroupsActivity.class);
		        startActivity(intent);
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
