package ch.epfl.sweng.studdybuddy.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.epfl.sweng.studdybuddy.R;

public class CourseAdapter extends RecyclerView.Adapter<Holder>
{
    List<String> list;

    //ajouter un constructeur prenant en entrée une liste
    public CourseAdapter(List<String> list)
    {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int itemType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_card, viewGroup, false);
        return new Holder(view, R.id.text);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(Holder holder, int position)
    {
        String course = list.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


}
