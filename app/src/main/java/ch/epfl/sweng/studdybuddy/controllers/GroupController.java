package ch.epfl.sweng.studdybuddy.controllers;

import android.os.Bundle;
import android.view.View;

import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.Resultable;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.app.Activity.RESULT_OK;

public final class GroupController {
    private GroupController() {

    }

    public static View.OnClickListener leaveOnClick(MetaGroupAdmin mga, String uId, Group group, Intentable destination) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) { //security checks done inside mga
                mga.clearListeners(); //to avoid unexpected behaviour
                mga.removeUserFromGroup(uId, group);
                destination.launch();
            }
        };
    }

    public static void processResult(int requestCode, int resultCode, Resultable res) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            res.onResult();
        }
    }

    public static View.OnClickListener inviteFriendsListener(Intentable toInviteFriends, String gId, String uId, int maxUsers){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Messages.userID, uId  );
                bundle.putString(Messages.groupID, gId);
                bundle.putInt(Messages.maxUser, maxUsers);
                GlobalBundle.getInstance().putAll(bundle);
                toInviteFriends.launch();
            }
        };
    }
}
