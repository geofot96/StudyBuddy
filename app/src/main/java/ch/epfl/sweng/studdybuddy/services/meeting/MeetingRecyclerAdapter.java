package ch.epfl.sweng.studdybuddy.services.meeting;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.SerialDate;

public class MeetingRecyclerAdapter extends RecyclerView.Adapter<MeetingRecyclerAdapter.ViewHolder> {

    private List<Meeting> meetingList;
    private Context context;


    public MeetingRecyclerAdapter(List<Meeting> meetingList, Context context) {
        this.meetingList = meetingList;
        this.meetingList.sort(new Comparator<Meeting>() {
            @Override
            public int compare(Meeting o1, Meeting o2) {
                return o1.getStarting().compareTo(o2.getStarting());
            }
        });
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewDate;
        public TextView textViewLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = (TextView) itemView.findViewById(R.id.meetingDate);
            textViewLocation = (TextView) itemView.findViewById(R.id.meetingLocation);
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
        Date startingDate = meeting.getStarting();
        Date endingDate = meeting.getEnding();
        myViewHolder.textViewDate.setText(
                startingDate.getMonth() + "/" +
                        startingDate.getDay() +
                        " From: " + startingDate.getHours()+ " : " + startingDate.getMinutes() +
                        " To: "+ endingDate.getHours() + " : " + endingDate.getMinutes());
        myViewHolder.textViewLocation.setText(meeting.getLocation().getTitle());
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }
}
