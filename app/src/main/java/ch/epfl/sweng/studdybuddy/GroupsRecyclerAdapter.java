package ch.epfl.sweng.studdybuddy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupsRecyclerAdapter.MyViewHolder> implements Filterable
{
    private List<Group> groupList, filterList;
    FeedFilter filter;


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        public TextView groupCourseTextView;
        public TextView groupParticipantInfoTextView;
        public TextView groupLanguageTextView;
        public Button messageButton;
        public TextView groupCreationDateTextView;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            groupCourseTextView = (TextView) itemView.findViewById(R.id.group_course_name);
            groupParticipantInfoTextView = (TextView) itemView.findViewById(R.id.group_participant_info);
            groupLanguageTextView = (TextView) itemView.findViewById(R.id.group_language);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
            groupCreationDateTextView = (TextView) itemView.findViewById(R.id.creation_date);

        }
    }

    public GroupsRecyclerAdapter(List<Group> groupList)
    {
        this.groupList = groupList;
        this.filterList=groupList;
    }

    public List<Group> getGroupList() {
        return new ArrayList<>(groupList);
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = new ArrayList<>(groupList);
    }

    @Override
    public GroupsRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View groupCardView = inflater.inflate(R.layout.recycle_viewer_row, parent, false);
        MyViewHolder vh = new MyViewHolder(groupCardView);
        return vh;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Group group = groupList.get(position);
        TextView newGroupCourseTextView = holder.groupCourseTextView;
        newGroupCourseTextView.setText(group.getCourse().getCourseName());
        TextView newGroupLanguageTextView = holder.groupLanguageTextView;
        newGroupLanguageTextView.setText(group.getLang());
        TextView newGroupParticipantInfoTextView = holder.groupParticipantInfoTextView;
        newGroupParticipantInfoTextView.setText(("Particip: " + group.getParticipantNumber() + "/" + group.getMaxNoUsers()));
        TextView newGroupCreationDateTextView = holder.groupCreationDateTextView;
        newGroupCreationDateTextView.setText(String.format("%d:%d:%d", group.getCreationDate().getDay(), group.getCreationDate().getMonth(), group.getCreationDate().getYear()));

        Button button = holder.messageButton;
        button.setText("More info");
    }

    @Override
    public int getItemCount()
    {
        return groupList.size();
    }
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new FeedFilter(this,filterList);
        }

        return filter;
    }
}
