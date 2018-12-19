package ch.epfl.sweng.studdybuddy.tools;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Specialised RecyclerView Viewholder containing also a textView
 */
public class Holder extends RecyclerView.ViewHolder {
    private TextView txt;

    /**
     * Create a Viewholder and also add a textView
     * @param itemView the itemView used to create the ViewHolder
     * @param id the id of the graphical element representing the TextView
     */
    public Holder(View itemView, @IdRes int id)
    {
        super(itemView);
        txt = (TextView) itemView.findViewById(id);
    }

    /**
     * getter for the tet inside the Textview
     * @return return the string contained in the TextView
     */
    public String get()
    {
        return txt.getText().toString();
    }

    /**
     * Set some text inside the textviewer
     * @param value tet to be set in the Textview
     */
    public void bind(String value)
    {
        txt.setText(value);
    }
}
