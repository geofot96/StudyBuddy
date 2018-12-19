package ch.epfl.sweng.studdybuddy.tools;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.ChatActivity;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.util.Messages;

/**
 * A specialised adapter used for chat elements in a recyclerAdapter
 */
public class ChatRecyclerAdapter extends BasicRecyclerAdapter
{
    /**
     * Constructor for the specialised recyclerAdapter
     * @param mg Metagroup used to get data from Firebase
     * @param fr The FirebaseReference
     * @param groupList List of groups that will be used as chat cards
     * @param userId id of the Current user
     */
    public ChatRecyclerAdapter(MetaGroup mg, FirebaseReference fr, List<Group> groupList, String userId) {
        setGroupList(groupList);
        setFilterList(groupList);
        setMb(mg);
        fb = fr;
        setUserId(userId);
        setuGroups(new ArrayList<>());
        setSizes(new HashMap<>());
        setuGroupIds(new ArrayList<>());
        getMb().addListenner(new RecyclerAdapterAdapter(this));
        getMb().getUserGroups(userId, getuGroupIds(), getuGroups());
        getMb().getAllGroupSizes(getSizes());
    }

    /**
     * Constructor for the specialised recyclerAdapter that uses the default Metagroup and FirebaseReference
     * @param groupList List of groups that will be used as chat cards
     * @param userId id of the Current user
     */
    public ChatRecyclerAdapter(List<Group> groupList, String userId)
    {

        this(new MetaGroup(), new FirebaseReference(), groupList, userId);
    }

    /**
     * Inflates the elements of the viewGrop
     * @param parent the viewGroup containing the different views
     * @param i the index of the element in the child list
     * @return A viewHolder with the inflated element
     */
    @NonNull
    @Override
    public BasicRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View groupCardView = inflater.inflate(R.layout.recycle_viewer_row_chat_list, parent, false);
        return new MyViewHolder(groupCardView);

    }

    /**
     * Sets the information retrieved from the list of groups to the holder
     * @param holder The holder of the graphical elements of the card
     * @param position the index of the element in the child list
     */
    @Override
    public void onBindViewHolder(@NonNull BasicRecyclerAdapter.MyViewHolder holder, int position)
    {
        Group group = getGroupList().get(position);
        holder.getGroupCourseTextView().setText(group.getCourse().getCourseName());
        holder.getGroupLanguageTextView().setText(group.getLang());
        setParticipantNumber(holder.getGroupParticipantInfoTextView(), group);
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.itemView.getContext(), ChatActivity.class);
                i.putExtra(Messages.groupID, group.getGroupID().getId());
                v.getContext().startActivity(i);

            }
        });
        if (getUserId().equals(group.getAdminID())) {
            holder.getAdmin().setText("\uD83D\uDC51");
        } else {
            holder.getAdmin().setText("");
        }

    }


    /**
     *
     * @return returns the size of the list of groups
     */
    @Override
    public int getItemCount()
    {
        return getGroupList().size();
    }
}
