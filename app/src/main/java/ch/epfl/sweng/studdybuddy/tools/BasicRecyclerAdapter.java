package ch.epfl.sweng.studdybuddy.tools;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.util.FeedFilter;

public abstract class BasicRecyclerAdapter extends RecyclerView.Adapter<BasicRecyclerAdapter.MyViewHolder>{
    //will have to make it private + accessors
        public List<Group> groupList, filterList;
        FeedFilter filter;
        public MetaGroup mb;
        public ReferenceWrapper fb;
        public String userId;
        public List<Group> uGroups;
        public HashMap<String, Integer> sizes;
        public List<String> uGroupIds;
        public Consumer<Intent> joinConsumer;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        public TextView groupCourseTextView;
        public TextView groupParticipantInfoTextView;
        public TextView groupLanguageTextView;
        public Button messageButton;
        public TextView groupCreationDateTextView;
        public TextView admin;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            groupCourseTextView = (TextView) itemView.findViewById(R.id.group_course_name);
            groupParticipantInfoTextView = (TextView) itemView.findViewById(R.id.group_participant_info);
            groupLanguageTextView = (TextView) itemView.findViewById(R.id.group_language);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
            groupCreationDateTextView = (TextView) itemView.findViewById(R.id.creation_date);
            admin = (TextView) itemView.findViewById(R.id.admin);
        }
    }

    public MyViewHolder getViewHolder(View itemView){ return new MyViewHolder(itemView);}

    public BasicRecyclerAdapter(){}

    public BasicRecyclerAdapter(List<Group> groupList, String userId)
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


    public void setParticipantNumber(TextView pNumber, Group group){
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
