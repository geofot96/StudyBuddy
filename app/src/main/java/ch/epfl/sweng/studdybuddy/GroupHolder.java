package ch.epfl.sweng.studdybuddy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class GroupHolder extends RecyclerView.ViewHolder {

    private TextView textViewView;

    public GroupHolder(View itemView)
    {
        super(itemView);
        textViewView = (android.widget.TextView) itemView.findViewById(R.id.text);
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
