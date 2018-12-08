package ch.epfl.sweng.studdybuddy.util;

import ch.epfl.sweng.studdybuddy.Fragments.SettingsFragment;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

public class SettingsFragmentHelper {

    public static Consumer<User> updateLanguage(SettingsFragment fragment){
        return new Consumer<User>() {
            @Override
            public void accept(User user) {
                fragment.updateLanguage(user);
            }
        };
    }
}
