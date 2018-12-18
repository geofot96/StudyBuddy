package ch.epfl.sweng.studdybuddy.tools;

import java.util.List;

import ch.epfl.sweng.studdybuddy.R;

/**
 * An adapter specialized in Courses
 */
public class CourseAdapter extends Adapter {

    List<String> list;

    //ajouter un constructeur prenant en entrée une liste

    public CourseAdapter(List<String> list) {
        super(R.layout.course_card, R.id.text);
        this.list = list;
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String course = list.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
