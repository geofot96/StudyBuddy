package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.UUID;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Course;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.tools.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.tools.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.showDropdown;
/**
 * An activity used by the user to create a new group with specific course, language and participant number limit
 */
public class CreateGroupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String selectedCourse = "";
    private String selectedLanguage;
    private int maxParticipants = 2;//default value
    private static List<String> coursesDB;
    private static final List<String> courseSelection = new ArrayList<>();
    FirebaseReference firebase;
    MetaGroup mb;
    Button create;

    /**
     * OnCreate method setting up all the graphical components
     * @param savedInstanceState  The state of the previous instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        create = (Button) findViewById(R.id.confirmGroupCreation);
        create.setEnabled(false);
    }

    /**
     * Prepares the text field which suggests courses as the user types
     * @return An ArrayAdapter of the suggested courses
     */
    ArrayAdapter<String> setUpAutoComplete() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.courseComplete2);
        textView.setAdapter(adapter);
        textView.setOnClickListener(showDropdown(textView));
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCourse = parent.getItemAtPosition(position).toString();
                ;
                create.setEnabled(true);
            }
        });
        return adapter;
    }

    /**
     * Set the limits of the graphical numberPicker
     */
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
        List<String> languagesList = Arrays.asList("\uD83C\uDDEC\uD83C\uDDE7", "\uD83C\uDDEB\uD83C\uDDF7", "\uD83C\uDDE9\uD83C\uDDEA", "\uD83C\uDDEE\uD83C\uDDF9");
        ArrayAdapter<String> dataAdapterLanguages = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languagesList);
        dataAdapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(dataAdapterLanguages);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        switch (parent.getId()) {
            case R.id.spinnerLanguage:
                selectedLanguage = item;
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addtoGroups(View view) {

        User user = ((StudyBuddy) CreateGroupActivity.this.getApplication()).authendifiedUser;
        Group g = new Group(maxParticipants, new Course(selectedCourse), selectedLanguage, UUID.randomUUID().toString(), user.getUserID().getId());
        mb.pushGroup(g, user.getUserID().getId());
        createUserInitialAvailabilities(user.getUserID().getId(), g.getGroupID().getId());
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            maxParticipants = numberPicker.getValue();
        }
    };

    private void createUserInitialAvailabilities(String user, String group) {
        Availability a = new ConnectedAvailability(user, group);
    }
}
