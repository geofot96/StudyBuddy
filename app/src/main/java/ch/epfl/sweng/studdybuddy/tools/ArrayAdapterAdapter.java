package ch.epfl.sweng.studdybuddy.tools;

import android.widget.ArrayAdapter;

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
