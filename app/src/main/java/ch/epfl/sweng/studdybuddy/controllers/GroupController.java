package ch.epfl.sweng.studdybuddy.controllers;

import android.view.View;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.Intentable;

public final class GroupController {
    private GroupController() {
        throw new IllegalStateException();
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
}
