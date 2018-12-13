package ch.epfl.sweng.studdybuddy.tools;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.epfl.sweng.studdybuddy.R;

public class BuddyHolder extends RecyclerView.ViewHolder {
    private TextView buddyName;
    private Button buddyInvite;
    public BuddyHolder(View itemView)
    {
        super(itemView);
        buddyName = (TextView) itemView.findViewById(R.id.buddy);
        buddyInvite = (Button) itemView.findViewById(R.id.invite);
    }

    public String get()
    {
        return buddyName.getText().toString();
    }

    public void bind(String name, boolean invite)
    {
        buddyName.setText(name);
        if(invite){
            buddyInvite.setText("Invite");
        }else{
            buddyInvite.setEnabled(false);
            buddyInvite.setVisibility(View.INVISIBLE);
        }
    }
}
