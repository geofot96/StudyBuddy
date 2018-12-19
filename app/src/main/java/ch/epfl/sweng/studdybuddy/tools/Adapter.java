package ch.epfl.sweng.studdybuddy.tools;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This class represent a specialised RecyclerView adapter
 */
public abstract class Adapter extends RecyclerView.Adapter<Holder> {
    @IdRes
    int holderId;
    @LayoutRes
    int layoutRes;

    /**
     * Consturctor of an adapter
     * @param layoutRes
     * @param holderId the unique id of the holder
     */
    public Adapter(@LayoutRes int layoutRes, @IdRes int holderId) {
        this.holderId = holderId;
        this.layoutRes = layoutRes;
    }

    /**
     * Creates a viewHolder and indicates what view to inflate
     * @param viewGroup The viewGroup from where the view gets inflated
     * @param itemType type of the item
     * @return returns a viewholder that indicates what view to inflate
     */
    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutRes, viewGroup, false);
        return new Holder(view, holderId);
    }

    /**
     * Initialises the RecyclerViewer
     * @param ctx the current context
     * @param rv RecyclerViewer to be initialised
     */
    public void initRecyclerView(Context ctx, RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(ctx));
        rv.setAdapter(this);
    }
}
