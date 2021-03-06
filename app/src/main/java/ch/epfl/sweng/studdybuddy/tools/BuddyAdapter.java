package ch.epfl.sweng.studdybuddy.tools;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;

public class BuddyAdapter extends RecyclerView.Adapter<BuddyHolder> {
    private List<User> buddies;
    private ID<Group> groupID;
    private List<User>  groupMembers;
    private int maxGroupSize;
    public BuddyAdapter(List<User> buddies, ID<Group> groupID, List<User> groupMembers, int maxGroupSize)
    {
        this.buddies = buddies;
        this.groupID = groupID;
        this.groupMembers = groupMembers;
        this.maxGroupSize = maxGroupSize;

    }


    @Override
    public void onBindViewHolder(BuddyHolder holder, int position)
    {
        User buddy = buddies.get(position);
        holder.bind(buddy.getName(), true, groupID, buddy.getUserID(), groupMembers.size() >= maxGroupSize);
    }

    @Override
    public int getItemCount()
    {
        return buddies.size();
    }



    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public BuddyHolder onCreateViewHolder(ViewGroup viewGroup, int itemType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buddy_card, viewGroup, false);
        return new BuddyHolder(view);
    }

    public void initRecyclerView(Context ctx, RecyclerView rv){
        rv.setLayoutManager(new LinearLayoutManager(ctx));
        rv.setAdapter(this);
    }

}
