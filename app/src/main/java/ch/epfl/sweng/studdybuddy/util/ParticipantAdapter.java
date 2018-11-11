package ch.epfl.sweng.studdybuddy.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantHolder> {

    List<User> participants;

    //ajouter un constructeur prenant en entrée une liste
    public ParticipantAdapter(List<User> participants)
    {
        this.participants = participants;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public ParticipantHolder onCreateViewHolder(ViewGroup viewGroup, int itemType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.participant_card, viewGroup, false);
        return new ParticipantHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(ParticipantHolder holder, int position)
    {
        String username = participants.get(position).getName();
        holder.bind(username);
    }

    @Override
    public int getItemCount()
    {
        return participants.size();
    }


}
