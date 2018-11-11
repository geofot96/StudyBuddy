package ch.epfl.sweng.studdybuddy.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ch.epfl.sweng.studdybuddy.R;

public class ParticipantHolder extends RecyclerView.ViewHolder{

    private TextView textViewView;

    //itemView est la vue correspondante à 1 cellule
    public ParticipantHolder(View itemView)
    {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textViewView = (TextView) itemView.findViewById(R.id.participant);
    }

    public String get()
    {
        return textViewView.getText().toString();
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(String course)
    {
        textViewView.setText(course);
    }
}
