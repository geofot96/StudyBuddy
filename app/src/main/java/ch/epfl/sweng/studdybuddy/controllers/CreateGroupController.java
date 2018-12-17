package ch.epfl.sweng.studdybuddy.controllers;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.services.calendar.Availability;
import ch.epfl.sweng.studdybuddy.services.calendar.ConnectedAvailability;
import ch.epfl.sweng.studdybuddy.tools.Intentable;

public class CreateGroupController {
    public static void joinGroupsAndGo(MetaGroup mb, User user, Group g, Intentable toNav) {
        mb.pushGroup(g, user.getUserID().getId());
        Availability a = new ConnectedAvailability(user.getUserID().getId(), g.getGroupID().getId());
        toNav.launch();
    }

    public static void createUserInitialAvailabilities(String user, String group){
        Availability a = new ConnectedAvailability(user, group);
    }
}
