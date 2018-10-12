package ch.epfl.sweng.studdybuddy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateGroup extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private String selectedSection;
    private String selectedCourse;
    private String selectedLanguage;
    private int maxParticipants=2;//default value
    private static final List<String> coursesDB= Arrays.asList("blablabl");
            //MainActivity.dummy.getListOfCourses().stream().map(x->x.getCourseName()).distinct().collect(Collectors.toList());


    private static final List<String> courseSelection = new ArrayList<>();
    private static AutoCompleteTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent intent = getIntent();

        //Section Spinner
        Spinner spinnerSection = (Spinner) findViewById(R.id.spinnerSection);
        spinnerSection.setOnItemSelectedListener(this);
        List<String> sectionsList = Arrays.asList("blablabl");
                //MainActivity.dummy.getListOfCourses().stream().map(x->x.getSection()).distinct().collect(Collectors.toList());
        ArrayAdapter<String> dataAdapterSections = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sectionsList);
        dataAdapterSections.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSection.setAdapter(dataAdapterSections);

        //Language spinner
        Spinner spinnerLanguage = (Spinner) findViewById(R.id.spinnerLanguage);
        spinnerLanguage.setOnItemSelectedListener(this);
        List<String> languagesList = Arrays.asList("blablabl");
                //MainActivity.dummy.getListOfCourses().stream().map(x->x.getLanguage()).distinct().collect(Collectors.toList());//TODO change this to a separate list of only languages
        ArrayAdapter<String> dataAdapterLanguages = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languagesList);
        dataAdapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(dataAdapterLanguages);

        //Number picker
        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(2);
        np.setMaxValue(10);
        np.setOnValueChangedListener(onValueChangeListener);

        //FRED
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, coursesDB);
        textView = (AutoCompleteTextView)findViewById(R.id.courseComplete2);
        textView.setAdapter(adapter);
        textView.setThreshold(0);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView textView = (AutoCompleteTextView)
                        findViewById(R.id.courseComplete2);
                textView.showDropDown();
            }
        });
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textInput = parent.getItemAtPosition(position).toString();
                selectedCourse=textInput;


            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        switch (parent.getId()){
            case R.id.spinnerSection:
                selectedSection=item;
                break;
            case R.id.spinnerLanguage:
                selectedLanguage=item;
                break;
                
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addtoGroups(View view)
    {
        MainActivity.groupList1.add(new Group(maxParticipants, new Course(selectedCourse,selectedLanguage,selectedSection),MainActivity.usersList1));//TODO add only logged in user
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }
    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                         maxParticipants=  numberPicker.getValue();
                }
            };
}
