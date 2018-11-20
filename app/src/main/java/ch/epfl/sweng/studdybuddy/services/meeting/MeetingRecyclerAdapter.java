package ch.epfl.sweng.studdybuddy.services.meeting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;

public class MeetingRecyclerAdapter extends RecyclerView.Adapter<MeetingRecyclerAdapter.ViewHolder> {

    private List<Meeting> meetingList;


    public MeetingRecyclerAdapter(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewDate;
        TextView textViewLocation;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = (TextView) itemView.findViewById(R.id.meetingDate);
            textViewLocation = (TextView) itemView.findViewById(R.id.meetingLocation);
        }

        //inject dependencies for testing
        public ViewHolder(@NonNull View itemView, TextView t1, TextView t2){
            super(itemView);
            textViewDate = t1;
            textViewLocation = t2;
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
        int RealMonth = startingDate.getMonth() + 1;
        int startMinutes = startingDate.getMinutes();
        int endMinutes = endingDate.getMinutes();
        myViewHolder.textViewDate.setText(
                RealMonth + "/" +
                        startingDate.getDate() +
                        " From: " + startingDate.getHours()+ ":" + startMinutes/10 + startMinutes%10 +
                        " To: "+ endingDate.getHours() + ":" + endMinutes/10 + startMinutes%10);
        myViewHolder.textViewLocation.setText(meeting.getLocation().getTitle());
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }
}
