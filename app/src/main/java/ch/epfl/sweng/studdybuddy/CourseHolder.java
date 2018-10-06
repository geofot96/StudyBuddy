package ch.epfl.sweng.studdybuddy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CourseHolder extends RecyclerView.ViewHolder {
    private TextView textViewView;

    //itemView est la vue correspondante à 1 cellule
    public CourseHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        textViewView = (TextView) itemView.findViewById(R.id.text);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(String course){
        textViewView.setText(course);
    }
}
