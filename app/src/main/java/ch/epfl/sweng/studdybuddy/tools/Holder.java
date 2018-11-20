package ch.epfl.sweng.studdybuddy.tools;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class Holder extends RecyclerView.ViewHolder {
    private TextView txt;

    public Holder(View itemView, @IdRes int id)
    {
        super(itemView);
        txt = (TextView) itemView.findViewById(id);
    }

    public String get()
    {
        return txt.getText().toString();
    }

    public void bind(String value)
    {
        txt.setText(value);
    }
}
