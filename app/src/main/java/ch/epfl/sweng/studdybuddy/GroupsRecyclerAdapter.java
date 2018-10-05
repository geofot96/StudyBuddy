package ch.epfl.sweng.studdybuddy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupsRecyclerAdapter.MyViewHolder> {
    private ArrayList<Group> groupList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView groupCourseTextView;
        public TextView groupParticipantInfoTextView;
        public Button messageButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            groupCourseTextView=(TextView)itemView.findViewById(R.id.group_course_name);
            groupParticipantInfoTextView=(TextView)itemView.findViewById(R.id.group_participant_info);
            messageButton=(Button)itemView.findViewById(R.id.message_button);
        }
    }

    public GroupsRecyclerAdapter(ArrayList<Group> groupList) {
        this.groupList=groupList;
    }

    @Override
    public GroupsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View groupCardView = inflater.inflate(R.layout.recycle_viewer_row, parent, false);
        MyViewHolder vh = new MyViewHolder(groupCardView);
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      Group group=groupList.get(position);
        TextView newGroupCourseTextView = holder.groupCourseTextView;
        newGroupCourseTextView.setText((String)group.getCourse().getCouseName());
        TextView newGroupParticipantInfoTextView = holder.groupParticipantInfoTextView;
        newGroupParticipantInfoTextView.setText(("Participants: "+group.getParticipantNumber()+"/"+group.getMaxParticipantNumber()));
        Button button= holder.messageButton;
        button.setText("More info");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return groupList.size();
    }
}