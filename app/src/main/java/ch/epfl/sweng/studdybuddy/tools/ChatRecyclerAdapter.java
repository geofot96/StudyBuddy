package ch.epfl.sweng.studdybuddy.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.ChatActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.util.FeedFilter;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.MyViewHolder>
{
    private List<Group> groupList, filterList;
    FeedFilter filter;
    private MetaGroup mb;
    private ReferenceWrapper fb;
    private String userId;
    private List<Group> uGroups;
    private HashMap<String, Integer> sizes;
    private List<String> uGroupIds;
    public Consumer<Intent> joinConsumer;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        public TextView groupCourseTextView;
        public TextView groupParticipantInfoTextView;
        public TextView groupLanguageTextView;
        public TextView admin;

        public MyViewHolder(View itemView) {
            super(itemView);
            groupCourseTextView = (TextView) itemView.findViewById(R.id.group_course_name);
            groupParticipantInfoTextView = (TextView) itemView.findViewById(R.id.group_participant_info);
            groupLanguageTextView = (TextView) itemView.findViewById(R.id.group_language);
            admin = (TextView) itemView.findViewById(R.id.admin);

        }
    }

    public ChatRecyclerAdapter(List<Group> groupList, String userId)
    {
        this.groupList = groupList;
        this.filterList=groupList;
        mb = new MetaGroup();
        fb = new FirebaseReference();
        this.userId = userId;
        this.uGroups = new ArrayList<>();
        this.sizes = new HashMap<>();
        this.uGroupIds = new ArrayList<>();
        mb.addListenner(new RecyclerAdapterAdapter(this));
        mb.getUserGroups(userId, uGroupIds, uGroups);
        mb.getAllGroupSizes(sizes);
    }

    public ChatRecyclerAdapter(List<Group> groupList, String userId, Consumer<Intent> joinConsumer)
    {
        this(groupList, userId);
        this.joinConsumer = joinConsumer;
    }
    public List<Group> getGroupList() {
        return new ArrayList<>(groupList);
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    @Override
    public ChatRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View groupCardView = inflater.inflate(R.layout.recycle_viewer_row_chat_list, parent, false);
        MyViewHolder vh = new MyViewHolder(groupCardView);
        return vh;
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Group group = groupList.get(position);
        holder.groupCourseTextView.setText(group.getCourse().getCourseName());
        holder.groupLanguageTextView.setText(group.getLang());

        setParticipantNumber(holder.groupParticipantInfoTextView, group);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(holder.itemView.getContext(), ChatActivity.class);
                i.putExtra("GroupID",group.getGroupID().getId());
                v.getContext().startActivity(i);

            }
        });
        if(userId.equals(group.getAdminID()))
        {
            holder.admin.setText("\uD83D\uDC51");
        }
        else
        {
            holder.admin.setText("");
        }
    }

    @Override
    public int getItemCount()
    {
        return groupList.size();
    }











    private void setParticipantNumber(TextView pNumber, Group group){
        int count = 0;
       if(sizes.get(group.getGroupID().toString()) != null){
           count = sizes.get(group.getGroupID().toString());
       }
       pNumber.setText(("Particip: " + count+ "/" + group.getMaxNoUsers()));
    }
    public int getParticipantNumber(Group group){
        int count = 0;
        if(sizes.get(group.getGroupID().toString()) != null){
            count = sizes.get(group.getGroupID().toString());
        }
        return count;
    }
}
