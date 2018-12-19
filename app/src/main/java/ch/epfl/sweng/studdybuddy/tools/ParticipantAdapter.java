package ch.epfl.sweng.studdybuddy.tools;

import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;

/**
 * A specialised adapter containing a list of paricipants
 */
public class ParticipantAdapter extends Adapter {

    List<User> participants;

    /**
     * Create an adapter and also assign a list of participants to this adapter
     * @param participants the list of participants assigned to this adapter
     */
    public ParticipantAdapter(List<User> participants)
    {
        super(R.layout.participant_card, R.id.participant);
        this.participants = participants;
    }


    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects

    /**
     * Fill the holder with the information of the participant
     * @param holder the holder to be modified
     * @param position the position of the participant inside the list of participants
     */
    @Override
    public void onBindViewHolder(Holder holder, int position)
    {
        String username = participants.get(position).getName();
        holder.bind(username);
    }

    /**
     * Getter of the size of the participant list
     * @return the size of the participant list
     */
    @Override
    public int getItemCount()
    {
        return participants.size();
    }


}
