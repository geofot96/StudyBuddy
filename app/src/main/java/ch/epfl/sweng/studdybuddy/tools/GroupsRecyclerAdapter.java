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

import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.group.GroupActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.util.DateTimeHelper;
import ch.epfl.sweng.studdybuddy.util.FeedFilter;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static ch.epfl.sweng.studdybuddy.controllers.CreateGroupController.createUserInitialAvailabilities;
/**
 * A specialised adapter used for the elements of the group feed in a recyclerAdapter
 */
public class GroupsRecyclerAdapter extends BasicRecyclerAdapter implements Filterable {
    /**
     * Constructs an adapter using a list of groups and the current user id
     * @param groupList the list of groups related to the user
     * @param userId the id of the user
     */
    public GroupsRecyclerAdapter(List<Group> groupList, String userId) {
        super(groupList, userId);
    }

    /**
     * Constructs an adapter using a list of groups and the current user id, and a consumer used for joining the group
     * @param groupList the list of groups related to the user
     * @param userId the id of the user
     * @param joinConsumer consumer used for joining this group
     */
    public GroupsRecyclerAdapter(List<Group> groupList, String userId, Consumer<Intent> joinConsumer) {
        this(groupList, userId);
        setJoinConsumer(joinConsumer);
    }

    /**
     * Inflates the elements of the viewGrop
     * @param parent the viewGroup containing the different views
     * @param viewType the type of the view
     * @return A viewHolder with the inflated element
     */
    public BasicRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View groupCardView = inflater.inflate(R.layout.recycle_viewer_row, parent, false);
        return getViewHolder(groupCardView);
    }

    /**
     * Sets the information retrieved from the list of groups to the holder
     * @param holder The holder of the graphical elements of the card
     * @param position the index of the element in the child list
     */
    @Override
    public void onBindViewHolder(@NonNull BasicRecyclerAdapter.MyViewHolder holder, int position) {
        Group group = getGroupList().get(position);
        holder.groupCourseTextView.setText(group.getCourse().getCourseName());
        holder.groupLanguageTextView.setText(group.getLang());
        holder.groupCreationDateTextView.setText(getCreationDate(group.getCreationDate()));
        setParticipantNumber(holder.groupParticipantInfoTextView, group);
        setButton(holder.messageButton, group);
        if (getUserId().equals(group.getAdminID())) {
            holder.admin.setText("\uD83D\uDC51");
        } else {
            holder.admin.setText("");
        }
    }

    /**
     * setter for the creation  date of the Group
     * @param date long number representing the date
     * @return A string representing the date
     */
    private String getCreationDate(long date) {
        return DateTimeHelper.printLongDate(date);
    }

    /**
     *  getter of the number of elements
     * @return the number of items in the list of groups
     */
    @Override
    public int getItemCount() {
        return getGroupList().size();
    }

    /**
     * getter of the feed filter
     * @return the feedFilter being used
     */
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FeedFilter(this, getFilterList());
        }

        return filter;
    }

    /**
     * Setter for the filter of the list
     * @param newFilter the new filter to be set
     */
    public void setFilterList(List<Group> newFilter) {
        getFilter();
        filter.setFilterList(newFilter);
    }

    /**
     * Sets a functionality to the button depending on the number of participants in the group
     * @param button button used to trigger the different actions
     * @param group the group related to the holder where the button is
     */
    private void setButton(Button button, Group group) {
        Integer gSize = getSizes().get(group.getGroupID().toString());
        int groupSize = 1;
        if (gSize != null) {
            groupSize = gSize;
        }
        if (getuGroupIds().contains(group.getGroupID().getId())) {
            button.setText("More Info");
            button.setOnClickListener(moreInfoListenerIfInTheGroup(button, group));
        } else {
            button.setText("Join");
            button.setOnClickListener(joinButtonListener(group, button));

            button.setClickable(groupSize < group.getMaxNoUsers());
        }
    }

    /**
     * Listener for the button, used when the user is joining a group
     * @param group the group to be joined
     * @param button the button triggering the joining action
     * @return the aforementioned listener
     */
    private View.OnClickListener joinButtonListener(Group group, Button button) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair pair = new Pair(getUserId(), group.getGroupID().toString());
                fb.select(Messages.FirebaseNode.USERGROUP).select(Helper.hashCode(pair)).setVal(pair);
                createUserInitialAvailabilities(getUserId(), group.getGroupID().toString());
                if(getJoinConsumer() != null)
                {
                    Intent intent = new Intent(button.getContext(), GroupActivity.class);
                    intent.putExtra(Messages.groupID, group.getGroupID().getId());
                    intent.putExtra(Messages.userID, getUserId());
                    intent.putExtra(Messages.maxUser, group.getMaxNoUsers());
                    intent.putExtra(Messages.ADMIN, group.getAdminID());
                    getJoinConsumer().accept(intent);
                }
            }
        };
    }

    /**
     * Starts an intent transmitting all the necessary information
     * @param button the button triggering the action
     * @param group The information of the group extracted and transmitted
     */
    private void gotoGroups(Button button, Group group) {
        if (getJoinConsumer() != null) {
            Intent intent = new Intent(button.getContext(), GroupActivity.class);
            intent.putExtra(Messages.groupID, group.getGroupID().getId());
            intent.putExtra(Messages.maxUser, group.getMaxNoUsers());
            intent.putExtra(Messages.userID, getUserId());
            intent.putExtra(Messages.ADMIN, group.getAdminID());
            getJoinConsumer().accept(intent);
        }
    }

    /**
     * Listener for the button, used when the user cannot join the grop
     * @param group the group to be joined
     * @param button the button triggering the go to group info screen
     * @return
     */
    private View.OnClickListener moreInfoListenerIfInTheGroup(Button button, Group group) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoGroups(button, group);
            }
        };
    }


}
