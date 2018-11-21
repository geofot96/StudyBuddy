package ch.epfl.sweng.studdybuddy.activities.meetings;

import android.app.AlertDialog;
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
import ch.epfl.sweng.studdybuddy.activities.MapsActivity;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class createMeetingActivity extends AppCompatActivity {
    private Meeting meeting;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private TextView mDisplayStartTime;
    private TextView mDisplayEndTime;
    private Date startingDate = new Date();
    private Date endingDate = new Date();

    private TextView mDisplayLocation;
    private MeetingLocation meetingLocation;

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
        mDisplayDate.setOnClickListener(fromDate());
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setDateAndTextView(year,month, dayOfMonth);
            }
        };

        initDisplayTime();

        mDisplayLocation = findViewById(R.id.locationTitle);
        mDisplayLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(createMeetingActivity.this, MapsActivity.class);
                i.putExtra(Messages.groupID, groupID);
                i.putExtra(Messages.meetingID, meeting.getId().getId());
                startActivityForResult(i, 1);
            }
        });

        initSaveBtn();

    }

    private void initDisplayTime() {
        mDisplayStartTime = findViewById(R.id.timePicker);
        mDisplayStartTime.setOnClickListener(fromTime(forStarting()));

        mDisplayEndTime = findViewById(R.id.timePicker2);
        mDisplayEndTime.setOnClickListener(fromTime(forEnding()));

    }

    private void initSaveBtn() {
        saveBtn = findViewById(R.id.setMeeting);
        saveBtn.setEnabled(false);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting.setStarting(startingDate);
                meeting.setEnding(endingDate);
                meeting.setLocation(meetingLocation);
                metaM.pushMeeting(meeting, new ID<>(groupID));
                Intent intent = new Intent(createMeetingActivity.this, GroupActivity.class);
                intent.putExtras(origin);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            meetingLocation = new MeetingLocation(
                    data.getStringExtra(Messages.LOCATION_TITLE),
                    data.getStringExtra(Messages.ADDRESS),
                    data.getDoubleExtra(Messages.LATITUDE, 0),
                    data.getDoubleExtra(Messages.LONGITUDE, 0));
            mDisplayLocation.setText(meetingLocation.getTitle() + ": " + meetingLocation.getAddress());
            updateButton();
        }
    }

    private void updateButton(){
        boolean correctTimeSlot = endingDate.after(startingDate);
        boolean isTooLate = startingDate.before(new Date());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(correctTimeSlot && !isTooLate && meetingLocation != null){
                    saveBtn.setEnabled(true);
                }else{
                    saveBtn.setEnabled(false);
                    Toast.makeText(createMeetingActivity.this, "Please set a correct time slot", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void setDateAndTextView(int year, int month, int dayOfMonth) {
        int realMonth = month+1;
        mDisplayDate.setText(year + "/" + realMonth + "/" + dayOfMonth);
        setDate(startingDate, year, month, dayOfMonth);
        setDate(endingDate, year, month, dayOfMonth);
        updateButton();
    }


    private MyClickListener fromTime(TimePickerDialog.OnTimeSetListener listener){
        return new MyClickListener(listener);
    }

    private  MyClickListener fromDate(){
        return new MyClickListener(true);
    }

    private MyTimeSetListener forStarting(){
        return new MyTimeSetListener(startingDate, mDisplayStartTime);
    }

    private MyTimeSetListener forEnding(){
        return new MyTimeSetListener(endingDate, mDisplayEndTime);
    }

    private void setDate(Date date, int year, int month, int dayOfMonth){
        date.setYear(year-1900);
        date.setMonth(month);
        date.setDate(dayOfMonth);
    }


    private class MyClickListener implements View.OnClickListener{
        private boolean forDate;
        private TimePickerDialog.OnTimeSetListener listener;
        public MyClickListener(boolean forDate){
            super();
            this.forDate = forDate;
        }

        public MyClickListener(TimePickerDialog.OnTimeSetListener listener){
            super();
            this.listener = listener;
            this.forDate = false;
        }

        @Override
        public void onClick(View v) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            AlertDialog dialog;

            if(forDate){
                dialog = new DatePickerDialog(
                        createMeetingActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day
                );
            }else{
                dialog = new TimePickerDialog(
                        createMeetingActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        listener,
                        hour, minute, true
                );
            }
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    private class MyTimeSetListener implements TimePickerDialog.OnTimeSetListener{
        private Date dateToSet;
        private TextView viewToSet;
        private MyTimeSetListener(Date date, TextView view){
            super();
            dateToSet = date;
            viewToSet = view;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String amPm;
            if(hourOfDay >= 12){
                amPm = " PM";
            } else{
                amPm = " AM";
            }
            viewToSet.setText(hourOfDay%12 + " : " + minute/10 + minute%10 + amPm);
            dateToSet.setHours(hourOfDay);
            dateToSet.setMinutes(minute);
            updateButton();
        }
    }


    public void setStartingDate(Date date) {
        startingDate = date;
        updateButton();
    }


    public void setEndingDate(Date date){
        endingDate = date;
        updateButton();
    }

    public void setLocation(MeetingLocation setter){
        meetingLocation = setter;
        updateButton();
    }
}
