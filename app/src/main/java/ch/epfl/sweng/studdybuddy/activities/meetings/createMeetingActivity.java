package ch.epfl.sweng.studdybuddy.activities.meetings;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.GroupActivity;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class createMeetingActivity extends AppCompatActivity {
    private Meeting meeting;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private TextView mDisplayStartTime;
    private TextView mDisplayEndTime;
    private TimePickerDialog.OnTimeSetListener startTimeSetListener;
    private TimePickerDialog.OnTimeSetListener endTimeSetListener;
    private Date startingDate = new Date();
    private Date endingDate = new Date();

    private Bundle origin;
    private MetaMeeting metaM;
    private Button saveBtn;

    private String groupID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        meeting = new Meeting();
        metaM = new MetaMeeting();
        origin = getIntent().getExtras();

        assert origin != null;
        groupID = origin.getString(Messages.groupID);

        mDisplayDate = findViewById(R.id.datePicker);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        createMeetingActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);

                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });



        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int realMonth = month+1;
                mDisplayDate.setText(year + "/" + realMonth + "/" + dayOfMonth);
                startingDate.setYear(year);
                startingDate.setMonth(month);
                startingDate.setDate(dayOfMonth);
                endingDate.setYear(year);
                endingDate.setMonth(month);
                endingDate.setDate(dayOfMonth);
            }
        };

        mDisplayStartTime = findViewById(R.id.timePicker);
        mDisplayStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        createMeetingActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        startTimeSetListener,
                        hour, minute, true
                );
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String amPm;
                if(hourOfDay >= 12){
                    amPm = " PM";
                } else{
                    amPm = " AM";
                }
                mDisplayStartTime.setText(hourOfDay%12 + " : " + minute/10 + minute%10 + amPm);
                startingDate.setHours(hourOfDay);
                startingDate.setMinutes(minute);
                updateButton();
            }
        };


        mDisplayEndTime = findViewById(R.id.timePicker2);
        mDisplayEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        createMeetingActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        endTimeSetListener,
                        hour, minute, true
                );
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        endTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String amPm;
                if(hourOfDay >= 12){
                    amPm = " PM";
                } else{
                    amPm = " AM";
                }
                mDisplayEndTime.setText(hourOfDay%12 + " : " + minute/10 + minute%10 + amPm);
                endingDate.setHours(hourOfDay);
                endingDate.setMinutes(minute);
                updateButton();
            }
        };

        saveBtn = findViewById(R.id.setMeeting);
        saveBtn.setEnabled(false);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting.setStarting(startingDate);
                meeting.setEnding(endingDate);
                metaM.pushMeeting(meeting, new ID<>(groupID));
                Intent intent = new Intent(createMeetingActivity.this, GroupActivity.class);
                intent.putExtras(origin);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("ShowToast")
    public void updateButton(){
        int i = startingDate.compareTo(endingDate);
        if( i < 0){
            saveBtn.setEnabled(true);
        }else{
            saveBtn.setEnabled(false);
            Toast.makeText(this, "Please set a correct time slot", Toast.LENGTH_LONG).show();
        }
    }
}
