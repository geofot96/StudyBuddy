package ch.epfl.sweng.studdybuddy.tools;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.util.Helper;
import ch.epfl.sweng.studdybuddy.util.Messages;

public class BuddyHolder extends RecyclerView.ViewHolder
{
    private TextView buddyName;
    private Button buddyInvite;
    private ID<Group> groupID;
    private ID<User> userID;

    public BuddyHolder(View itemView)
    {
        super(itemView);
        buddyName = (TextView) itemView.findViewById(R.id.buddy);
        buddyInvite = (Button) itemView.findViewById(R.id.invite);
    }

    public View.OnClickListener getInvitationListener()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Pair pair = new Pair(userID.toString(), groupID.toString());
                FirebaseReference reference = new FirebaseReference();
                reference.select(Messages.FirebaseNode.USERGROUP).select(Helper.hashCode(pair)).setVal(pair);
                System.out.println(userID);
                buddyInvite.setText("INVITED");
                buddyInvite.setEnabled(false);
            }
        };
    }

    public String get()
    {
        return buddyName.getText().toString();
    }

    public void bind(String name, boolean invite, ID<Group> gID, ID<User> uID, boolean isFull)
    {
        buddyName.setText(name);
        if(invite &&  !isFull)
        {
            buddyInvite.setText("Invite");
        }else if (isFull){
            buddyInvite.setText("The group is full");
            buddyInvite.setEnabled(false);
            buddyInvite.setVisibility(View.INVISIBLE);
        }
        else
        {
            buddyInvite.setEnabled(false);
            buddyInvite.setVisibility(View.INVISIBLE);
        }
        this.groupID = gID;
        this.userID = uID;
        buddyInvite.setOnClickListener(getInvitationListener());

    }
}
