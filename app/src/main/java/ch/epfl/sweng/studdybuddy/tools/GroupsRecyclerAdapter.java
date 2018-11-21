package ch.epfl.sweng.studdybuddy.tools;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.Fragments.FeedFragment;
import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.GroupActivity;
import ch.epfl.sweng.studdybuddy.activities.GroupInfoActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.util.FeedFilter;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class GroupsRecyclerAdapter extends BasicRecyclerAdapter implements Filterable
{
    public GroupsRecyclerAdapter(List<Group> groupList, String userId)
    {
        super(groupList,userId);
    }

    public GroupsRecyclerAdapter(List<Group> groupList, String userId, Consumer<Intent> joinConsumer)
    {
        this(groupList, userId);
        setJoinConsumer(joinConsumer);
    }

    public BasicRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View groupCardView = inflater.inflate(R.layout.recycle_viewer_row, parent, false);
        BasicRecyclerAdapter.MyViewHolder vh = getViewHolder(groupCardView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BasicRecyclerAdapter.MyViewHolder holder, int position) {
        Group group = getGroupList().get(position);
        holder.groupCourseTextView.setText(group.getCourse().getCourseName());
        holder.groupLanguageTextView.setText(group.getLang());
        holder.groupCreationDateTextView.setText(getCreationDate(group));
        setParticipantNumber(holder.groupParticipantInfoTextView, group);
        setButton(holder.messageButton, group);
        if(getUserId().equals(group.getAdminID())) {
            holder.admin.setText("\uD83D\uDC51");
        }
        else {
            holder.admin.setText("");
        }
    }



    private String getCreationDate(Group group){
        return group.getCreationDate().toString();
    }



    @Override
    public int getItemCount()
    {
        return getGroupList().size();
    }
    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new FeedFilter(this,getFilterList());
        }

        return filter;
    }

    public void setFilterList(List<Group> newFilter)
    {
        getFilter();
        filter.setFilterList(newFilter);
    }

    private void setButton(Button button, Group group){
        Integer gSize = getSizes().get(group.getGroupID().toString());
        int groupSize = 1;
        if(gSize != null){
            groupSize = gSize.intValue();
        }
        button.setText("Join");
        if(groupSize < group.getMaxNoUsers()
                &&!getuGroupIds().contains(group.getGroupID().getId())) {
            button.setText("Join");
            button.setOnClickListener(joinButtonListener(group, button));
        }else {
            button.setText("More Info");
            getTheRightMoreInfo(button, group, getuGroupIds().contains(group.getGroupID().getId()));

        }
    }

    private void getTheRightMoreInfo(Button button, Group group, boolean contains) {
        if (contains) {
            button.setOnClickListener(moreInfoListenerIfInTheGroup(button, group));
        } else {
            button.setOnClickListener(moreInfoListener(button, group.getGroupID().getId()));
        }
    }

    private View.OnClickListener joinButtonListener(Group group, Button button){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair pair =new Pair(getUserId(), group.getGroupID().toString());
                fb.select("userGroup").select(Helper.hashCode(pair)).setVal(pair);
                Availability a = new ConnectedAvailability(pair.getKey(), pair.getValue());
                if(getJoinConsumer() != null)
                {
                    Intent intent = new Intent(button.getContext(), GroupActivity.class);
                    intent.putExtra(FeedFragment.GROUP_ID, group.getGroupID().getId());
                    intent.putExtra(Messages.userID, getUserId());
                    intent.putExtra(Messages.maxUser, group.getMaxNoUsers());
                    getJoinConsumer().accept(intent);
                }
            }
        };
    }

    private View.OnClickListener moreInfoListener(Button button, String gId){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getJoinConsumer() != null )
                {
                    Intent intent = new Intent(button.getContext(), GroupInfoActivity.class);
                    intent.putExtra(FeedFragment.GROUP_ID, gId);
                    getJoinConsumer().accept(intent);
                }
            }
        };
    }

    private View.OnClickListener moreInfoListenerIfInTheGroup(Button button, Group group){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getJoinConsumer() != null )
                {
                    Intent intent = new Intent(button.getContext(), GroupActivity.class);
                    intent.putExtra(FeedFragment.GROUP_ID, group.getGroupID().getId());
                    intent.putExtra(Messages.maxUser, group.getMaxNoUsers());
                    intent.putExtra(Messages.userID, getUserId());
                    intent.putExtra(Messages.ADMIN, group.getAdminID());
                    getJoinConsumer().accept(intent);
                }
            }
        };
    }


}
