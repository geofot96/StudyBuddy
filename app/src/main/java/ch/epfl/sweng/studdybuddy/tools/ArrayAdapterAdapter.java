package ch.epfl.sweng.studdybuddy.tools;

import android.widget.ArrayAdapter;

/**
 * Wrapper around an ArrayAdapter
 *  */
public class ArrayAdapterAdapter implements AdapterAdapter{
    ArrayAdapter adapter;
    public ArrayAdapterAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }


    @Override
    public void update() {
        adapter.notifyDataSetChanged();
    }
}
