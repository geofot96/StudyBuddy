package ch.epfl.sweng.studdybuddy.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ch.epfl.sweng.studdybuddy.R;

public class ParticipantHolder extends RecyclerView.ViewHolder{

    private TextView txt;

    public ParticipantHolder(View itemView)
    {
        super(itemView);
        txt = (TextView) itemView.findViewById(R.id.participant);
    }

    public String get()
    {
        return txt.getText().toString();
    }

    public void bind(String course)
    {
        txt.setText(course);
    }
}
