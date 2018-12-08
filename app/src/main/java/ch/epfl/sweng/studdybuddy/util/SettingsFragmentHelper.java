package ch.epfl.sweng.studdybuddy.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ch.epfl.sweng.studdybuddy.Fragments.SettingsFragment;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.auth.FirebaseAuthManager;
import ch.epfl.sweng.studdybuddy.auth.GoogleSignInActivity;
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

    public static View.OnClickListener signOutButtonOnClickLister(String uId, SettingsFragment settingsFragment){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsFragment.getContext(), GoogleSignInActivity.class);
                //If it's a travis test, don't logout from GoogleAuth
                if(uId.equals("Default")){
                    settingsFragment.startActivity(intent);
                }else {
                    (new FirebaseAuthManager(settingsFragment.getActivity(), uId)).logout().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            settingsFragment.startActivity(intent);
                        }
                    });
                }
                settingsFragment.getActivity().finish();

            }
        };
    }


    public static View.OnClickListener setUpLocationOnClickListener(SettingsFragment settingsFragment){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(settingsFragment.getActivity(), MapsActivity.class);
                i.putExtra(Messages.groupID, Messages.settingsPlaceHolder);
                i.putExtra(Messages.meetingID,Messages.settingsPlaceHolder);
                i.putExtra(Messages.ADMIN,Messages.settingsPlaceHolder);
                GlobalBundle.getInstance().putAll(i.getExtras());
                settingsFragment.startActivityForResult(i, 1);
            }
        };
    }



}
