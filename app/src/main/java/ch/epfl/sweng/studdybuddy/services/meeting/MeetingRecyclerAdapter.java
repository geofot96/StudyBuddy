package ch.epfl.sweng.studdybuddy.services.meeting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.util.DateTimeHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class MeetingRecyclerAdapter extends RecyclerView.Adapter<MeetingRecyclerAdapter.ViewHolder> {

    private List<Meeting> meetingList;
    private Context context;
    private Activity activity;
    private String groupID;
    private String adminID;

    public MeetingRecyclerAdapter(Context packageContext, Activity act, List<Meeting> meetingList, Pair group_adminIDs) {
        this.meetingList = meetingList;
        this.groupID = group_adminIDs.getKey();
        this.context = packageContext;
        this.activity = act;
        this.adminID = group_adminIDs.getValue();
        StudyBuddy s = (StudyBuddy) act.getApplication();
        String userID = s.getAuthendifiedUser().getUserID().getId();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewDate;
        TextView textViewLocation;
        CardView cardViewMeeting;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewMeeting = (CardView) itemView.findViewById(R.id.cardViewMeeting);
            textViewDate = (TextView) itemView.findViewById(R.id.meetingDate);
            textViewLocation = (TextView) itemView.findViewById(R.id.meetingLocation);
        }

        //inject dependencies for testing
        public ViewHolder(@NonNull View itemView, TextView t1, TextView t2, CardView c){
            super(itemView);
            textViewDate = t1;
            textViewLocation = t2;
            cardViewMeeting = c;
        }
    }


    @NonNull
    @Override
    public MeetingRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.meeting_card, viewGroup, false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MeetingRecyclerAdapter.ViewHolder myViewHolder, int i) {
        Meeting meeting = meetingList.get(i);
        myViewHolder.textViewDate.setText(DateTimeHelper.printMeetingDate(meeting.getStarting(), meeting.getEnding()));
        myViewHolder.textViewLocation.setText(meeting.getLocation().getTitle() + ": " + meeting.getLocation().getAddress());
        myViewHolder.cardViewMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra(Messages.groupID, groupID);
                intent.putExtra(Messages.meetingID, meeting.getId().getId());
                intent.putExtra(Messages.ADMIN, adminID);
                GlobalBundle.getInstance().putAll(intent.getExtras());
                startActivityForResult(activity, intent, 1, null);
            }
        });
    }


    @Override
    public int getItemCount() {
        return meetingList.size();
    }

}
