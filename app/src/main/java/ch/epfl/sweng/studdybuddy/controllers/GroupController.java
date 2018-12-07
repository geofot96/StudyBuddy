package ch.epfl.sweng.studdybuddy.controllers;

import android.view.View;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.Group;
import ch.epfl.sweng.studdybuddy.core.Pair;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.Notifiable;
import ch.epfl.sweng.studdybuddy.tools.Resultable;

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

    public static Consumer<List<Boolean>> callbackCalendar(Pair pair, Notifiable ctx) {
        return new Consumer<List<Boolean>>() {
            @Override
            public void accept(List<Boolean> booleans) {
                ctx.getNotified();
            }
        };
    }

    public static void processResult(int requestCode, int resultCode, Resultable res) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            res.onResult();
        }
    }
}
