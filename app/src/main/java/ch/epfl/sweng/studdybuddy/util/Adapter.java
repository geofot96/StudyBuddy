package ch.epfl.sweng.studdybuddy.util;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class Adapter extends RecyclerView.Adapter<Holder>{
    @IdRes int holderId;
    @LayoutRes int layoutRes;
    public Adapter(@LayoutRes int layoutRes, @IdRes int holderId){
        this.holderId = holderId;
        this.layoutRes = layoutRes;
    }
    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int itemType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutRes, viewGroup, false);
        return new Holder(view, holderId);
    }

    public void initRecyclerView(Context ctx, RecyclerView rv){
       rv.setLayoutManager(new LinearLayoutManager(ctx));
       rv.setAdapter(this);
    }
}
