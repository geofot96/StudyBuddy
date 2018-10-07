package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateGroup extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private String selectedSection;
    private String selectedCourse;
    private String selectedLanguage;
    private int maxParticipants=2;//default value


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent intent = getIntent();

        //This will be the first Spinner to be set, by default it will be the one on postition 0, once this is called,
        //the other spinners will be called due to the fact that "setOnItemSelect" is called by default
        Spinner spinnerCourses = (Spinner) findViewById(R.id.spinnerCourse);
        spinnerCourses.setOnItemSelectedListener(this);
        List<String> coursesList = MainActivity.dummy.getListOfCourses().stream().map(x->x.getCourseName()).distinct().collect(Collectors.toList());
        ArrayAdapter<String> dataAdapterCourses = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, coursesList);
        dataAdapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(dataAdapterCourses);

        NumberPicker np = findViewById(R.id.numberPicker);

        np.setMinValue(2);
        np.setMaxValue(10);
        np.setOnValueChangedListener(onValueChangeListener);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        switch (parent.getId()){

            case R.id.spinnerCourse:
                //This happens after course selection
                selectedCourse=item;
                Spinner spinnerSection = (Spinner) findViewById(R.id.spinnerSection);
                spinnerSection.setOnItemSelectedListener(this);
                List<String> sectionsList = MainActivity.dummy.getListOfCourses().stream().filter(y-> y.getCourseName()==selectedCourse).map(x->x.getSection()).distinct().collect(Collectors.toList());
                ArrayAdapter<String> dataAdapterSections = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sectionsList);
                dataAdapterSections.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSection.setAdapter(dataAdapterSections);

                break;
            case R.id.spinnerSection:
                //This happens after Section selection
                selectedSection=item;
                Spinner spinnerLanguage = (Spinner) findViewById(R.id.spinnerLanguage);
                spinnerLanguage.setOnItemSelectedListener(this);
                List<String> languagesList = MainActivity.dummy.getListOfCourses().stream().filter(y-> y.getCourseName()==selectedCourse&& y.getSection()==selectedSection).map(x->x.getLanguage()).distinct().collect(Collectors.toList());
                ArrayAdapter<String> dataAdapterLanguages = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languagesList);
                dataAdapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLanguage.setAdapter(dataAdapterLanguages);

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
