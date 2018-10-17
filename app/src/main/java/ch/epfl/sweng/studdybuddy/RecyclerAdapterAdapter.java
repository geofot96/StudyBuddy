package ch.epfl.sweng.studdybuddy;

import android.support.v7.widget.RecyclerView;

public class RecyclerAdapterAdapter implements AdapterAdapter {

    RecyclerView.Adapter adapter;

    public RecyclerAdapterAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void update() {
        adapter.notifyDataSetChanged();
    }
}
