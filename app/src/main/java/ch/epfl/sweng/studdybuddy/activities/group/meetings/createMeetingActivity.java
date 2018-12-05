package ch.epfl.sweng.studdybuddy.activities.group.meetings;

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

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import ch.epfl.sweng.studdybuddy.R;
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
import ch.epfl.sweng.studdybuddy.util.MapsHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;


public class createMeetingActivity extends AppCompatActivity {
    private Meeting meeting;

    private TextView mDisplayDate;

    private Date startingDate = new Date();
    private Date endingDate = new Date();

    private TextView mDisplayLocation;
    private MeetingLocation meetingLocation;

    private Bundle origin;
    private MetaMeeting metaM;
    private FirebaseReference ref;
    private Button saveBtn;
    private String groupID;
    private AdapterAdapter ButtonListener;
    String uId;
    private final String TAG = "CREATE_MEETING_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);
        meeting = new Meeting();
        metaM = new MetaMeeting();
        ref = new FirebaseReference();
        origin = GlobalBundle.getInstance().getSavedBundle();
        groupID = origin.getString(Messages.groupID);
        mDisplayDate = findViewById(R.id.datePicker);
        mDisplayDate.setOnClickListener(fromDate());
        uId = ((StudyBuddy) this.getApplication()).getAuthendifiedUser().getUserID().getId();

        ButtonListener = new AdapterAdapter() {
            @Override
            public void update() {
                updateButton();
            }
        };

        initDisplayTime();
        mDisplayLocation = findViewById(R.id.locationTitle);
        initMeetingLocation();
        setClickOnLocation();
        meetingLocation = MapsHelper.ROLEX_LOCATION;
        mDisplayLocation.setText(meetingLocation.getTitle() + ": " + meetingLocation.getAddress());
        initSaveBtn();
    }

    private void setClickOnLocation() {
        mDisplayLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(createMeetingActivity.this, MapsActivity.class);
                i.putExtra(Messages.groupID, groupID);
                i.putExtra(Messages.meetingID, meeting.getId().getId());
                i.putExtra(Messages.ADMIN, origin.getString(Messages.ADMIN));
                GlobalBundle.getInstance().putAll(i.getExtras());
                startActivityForResult(i, 1);
            }
        });
    }

    private void initDisplayTime() {
        TextView mDisplayStartTime = findViewById(R.id.timePicker);
        mDisplayStartTime.setOnClickListener(fromTime(mDisplayStartTime, startingDate));

        TextView mDisplayEndTime = findViewById(R.id.timePicker2);
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
                Log.i("DEBUG", "pushing");
                metaM.pushMeeting(meeting, new ID<>(groupID));
                Intent intent = new Intent(createMeetingActivity.this, GroupActivity.class);
                GlobalBundle.getInstance().putAll(origin);
                startActivity(intent);
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
        saveBtn.setEnabled(correctTimeSlot && !isTooLate && meetingLocation != null);
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
                        createMeetingActivity.this,
                        ActivityHelper.listenDate(mDisplayDate, startingDate,endingDate, ButtonListener),
                        year, month, day
                );
            }else{
                dialog = new TimePickerDialog(
                        createMeetingActivity.this,
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
