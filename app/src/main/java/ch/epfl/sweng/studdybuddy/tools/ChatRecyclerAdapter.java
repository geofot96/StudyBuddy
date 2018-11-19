package ch.epfl.sweng.studdybuddy.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

public class ChatRecyclerAdapter extends BasicRecyclerAdapter
{
    public ChatRecyclerAdapter(List<Group> groupList, String userId)
    {
        setGroupList(groupList);
        setFilterList(groupList);
        setMb(new MetaGroup());
        fb = new FirebaseReference();
        setUserId(userId);
        setuGroups(new ArrayList<>());
        setSizes(new HashMap<>());
        setuGroupIds(new ArrayList<>());
        getMb().addListenner(new RecyclerAdapterAdapter(this));
        getMb().getUserGroups(userId, getuGroupIds(), getuGroups());
        getMb().getAllGroupSizes(getSizes());
    }

    @NonNull
    @Override
    public BasicRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View groupCardView = inflater.inflate(R.layout.recycle_viewer_row_chat_list, parent, false);
        MyViewHolder vh = new MyViewHolder(groupCardView);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull BasicRecyclerAdapter.MyViewHolder holder, int position)
    {
        Group group = getGroupList().get(position);
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
        if(getUserId().equals(group.getAdminID()))
        {
            holder.admin.setText("\uD83D\uDC51");
        }
        else
        {
            holder.admin.setText("");
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
