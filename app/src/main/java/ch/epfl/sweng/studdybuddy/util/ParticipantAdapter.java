package ch.epfl.sweng.studdybuddy.util;

import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;

public class ParticipantAdapter extends Adapter {

    List<User> participants;

    //ajouter un constructeur prenant en entrée une liste
    public ParticipantAdapter(List<User> participants)
    {
        super(R.layout.participant_card, R.id.participant);
        this.participants = participants;
    }


    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(Holder holder, int position)
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
