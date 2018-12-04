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

public class ChatRecyclerAdapter extends BasicRecyclerAdapter
{

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
    public ChatRecyclerAdapter(List<Group> groupList, String userId)
    {

        this(new MetaGroup(), new FirebaseReference(), groupList, userId);
    }

    @NonNull
    @Override
    public BasicRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View groupCardView = inflater.inflate(R.layout.recycle_viewer_row_chat_list, parent, false);
        return new MyViewHolder(groupCardView);

    }

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
                Intent i=new Intent(holder.itemView.getContext(), ChatActivity.class);
                i.putExtra(Messages.groupID,group.getGroupID().getId());
                v.getContext().startActivity(i);

            }
        });
        if(getUserId().equals(group.getAdminID()))
        {
            holder.getAdmin().setText("\uD83D\uDC51");
        }
        else
        {
            holder.getAdmin().setText("");
        }

    }


//    @SuppressLint("DefaultLocale")
////    @Override
////    public void onBindViewHolder(MyViewHolder holder, int position){
////
////    }

    @Override
    public int getItemCount()
    {
        return getGroupList().size();
    }
}
