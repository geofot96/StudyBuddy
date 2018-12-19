package ch.epfl.sweng.studdybuddy.controllers;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.tools.Intentable;

public class CreateGroupController {
    public static void joinGroupsAndGo(MetaGroup mb, User user, Group g, Intentable toNav) {
        mb.pushGroup(g, user.getUserID().getId());
        ConnectedAvailability.createNewAvailabilities(user.getUserID(), g.getGroupID());
        toNav.launch();
    }

    public static void createUserInitialAvailabilities(String user, String group){
        ConnectedAvailability.createNewAvailabilities(new ID<>(user), new ID<>(group));
    }
}
