package ch.epfl.sweng.studdybuddy.activities.group.meetings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.GroupActivity;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaMeeting;
import ch.epfl.sweng.studdybuddy.services.meeting.Meeting;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.ActivityHelper;
import ch.epfl.sweng.studdybuddy.util.DateTimeHelper;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.RequestCodes;



public class CreateMeetingActivity extends AppCompatActivity {
    private Meeting meeting;

    private TextView mDisplayDate;
    private TextView mDisplayStartTime;
    private TextView mDisplayEndTime;

    private Date startingDate = new Date();
    private Date endingDate = new Date();

    private TextView mDisplayLocation;
    private MeetingLocation meetingLocation;

    private Bundle origin;
    private MetaMeeting metaM;
    private FirebaseReference ref;
    private Button saveBtn;
    private String groupID;

    private String adminID;
    private String userID;

    private AdapterAdapter ButtonListener;
    String uId;
    private final String TAG = "CREATE_MEETING_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        metaM = new MetaMeeting();
        ref = new FirebaseReference();
        origin = GlobalBundle.getInstance().getSavedBundle();
        groupID = origin.getString(Messages.groupID);
        adminID = origin.getString(Messages.ADMIN);
        userID = origin.getString(Messages.userID);

        if(groupID == null || adminID == null || userID == null){
            startActivity(new Intent(this, NavigationActivity.class));
            Log.d(TAG, "Information of the group is not fully recovered");
        }

        mDisplayDate = findViewById(R.id.datePicker);

        mDisplayStartTime = findViewById(R.id.timePicker);
        mDisplayEndTime = findViewById(R.id.timePicker2);

        ButtonListener = getAdapter();

        initDisplays();

        mDisplayLocation = findViewById(R.id.locationTitle);
        initDisplayLocation();

        initSaveBtn();

        meeting = onStartForResult();
    }


    private AdapterAdapter getAdapter() {
        return new AdapterAdapter() {
            @Override
            public void update() {
                updateButton();
            }
        };
    }


    @SuppressLint("SetTextI18n")
    private Meeting onStartForResult(){
        if(getCallingActivity() != null) {
            mDisplayDate.setText(DateTimeHelper.printLongDate(origin.getLong(Messages.M_SDATE)));
            mDisplayLocation.setText(origin.getString(Messages.LOCATION_TITLE) + ": " + origin.getString(Messages.ADDRESS));
            mDisplayStartTime.setText(DateTimeHelper.printTime(origin.getLong(Messages.M_SDATE)));
            mDisplayEndTime.setText(DateTimeHelper.printTime(origin.getLong(Messages.M_EDATE)));
            startingDate.setTime(origin.getLong(Messages.M_SDATE));
            endingDate.setTime(origin.getLong(Messages.M_EDATE));
            meetingLocation = new MeetingLocation(origin.getString(Messages.LOCATION_TITLE), origin.getString(Messages.ADDRESS), origin.getDouble(Messages.LATITUDE), origin.getDouble(Messages.LONGITUDE));
            updateButton();
            return new Meeting(origin.getLong(Messages.M_SDATE), origin.getLong(Messages.M_EDATE), meetingLocation, origin.getString(Messages.meetingID));
        }
        return new Meeting();

    }

    private void initDisplayLocation() {
        mDisplayLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateMeetingActivity.this, MapsActivity.class);
                i.putExtra(Messages.groupID, groupID);
                i.putExtra(Messages.meetingID, meeting.getId().getId());
                i.putExtra(Messages.ADMIN, adminID);
                GlobalBundle.getInstance().putAll(i.getExtras());
                startActivityForResult(i, RequestCodes.MAPS.getRequestCode());
            }
        });
    }

    private void initDisplays() {
        mDisplayDate.setOnClickListener(fromDate());
        mDisplayStartTime.setOnClickListener(fromTime(mDisplayStartTime, startingDate));
        mDisplayEndTime.setOnClickListener(fromTime(mDisplayEndTime, endingDate));
    }

    private void initSaveBtn() {
        saveBtn = findViewById(R.id.setMeeting);
        saveBtn.setEnabled(false);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meeting.setStarting(startingDate.getTime());
                meeting.setEnding(endingDate.getTime());
                meeting.setLocation(meetingLocation);
                Intent intent;
                if(getCallingActivity() != null){
                    GlobalBundle.getInstance().putMeeting(meeting);
                    intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    metaM.pushMeeting(meeting, new ID<>(groupID));
                    intent = new Intent(CreateMeetingActivity.this, GroupActivity.class);
                    GlobalBundle.getInstance().putAll(origin);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent d){
        super.onActivityResult(requestCode,resultCode,d);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle data = GlobalBundle.getInstance().getSavedBundle();
            meetingLocation = new MeetingLocation(
                    data.getString(Messages.LOCATION_TITLE),
                    data.getString(Messages.ADDRESS),
                    data.getDouble(Messages.LATITUDE, 0),
                    data.getDouble(Messages.LONGITUDE, 0));
            mDisplayLocation.setText(meetingLocation.getTitle() + ": " + meetingLocation.getAddress());
            updateButton();
        }
    }

    private void updateButton(){
        boolean correctTimeSlot = endingDate.after(startingDate);
        boolean isTooLate = startingDate.before(new Date());

        if(correctTimeSlot && !isTooLate && meetingLocation != null){
            saveBtn.setEnabled(true);
        }else{
            saveBtn.setEnabled(false);
            setCorrectToastMessage(correctTimeSlot, isTooLate);
        }

    }

    private void setCorrectToastMessage(boolean correctTimeSlot, boolean isTooLate) {
        String msg = "The location was not chosen";
        String msg1 = "The meeting is wrongly scheduled: ";

        if(!correctTimeSlot){
            msg = msg1 + "the time slot is incorrect";
        }

        if(isTooLate){
            msg = msg1 + "the meeting starts in the past";
        }

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private void initMeetingLocation(){
        ref.select(Messages.FirebaseNode.USERS).select(uId).get(User.class, new Consumer<User>() {
            @Override
            public void accept(User user) {
                meetingLocation = user.getFavoriteLocation() != null ? user.getFavoriteLocation() : MapsHelper.ROLEX_LOCATION;
                mDisplayLocation.setText(meetingLocation.toString());
            }
        });
    }


    private MyClickListener fromTime(TextView tv, Date d){
        return new MyClickListener(tv, d);
    }

    private  MyClickListener fromDate(){
        return new MyClickListener(true);
    }

    private class MyClickListener implements View.OnClickListener{
        private boolean forDate;
        private TextView mDisplayTime;
        private Date dateToSet;

        public MyClickListener(boolean forDate){
            super();
            this.forDate = forDate;
        }

        public MyClickListener(TextView tv, Date d){
            super();
            this.mDisplayTime = tv;
            this.dateToSet = d ;
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
                        CreateMeetingActivity.this,
                        ActivityHelper.listenDate(mDisplayDate, startingDate,endingDate, ButtonListener),
                        year, month, day
                );
            }else{
                dialog = new TimePickerDialog(
                        CreateMeetingActivity.this,
                        ActivityHelper.listenTime(mDisplayTime, dateToSet, ButtonListener),
                        hour, minute, true
                );
            }
            Objects.requireNonNull(dialog.getWindow());
            dialog.show();
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
