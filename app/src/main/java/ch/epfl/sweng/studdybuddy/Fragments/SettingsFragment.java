package ch.epfl.sweng.studdybuddy.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinnerLang;
    String favoriteLanguage;
    User user;
    String userId;
    String gId;
    FirebaseReference ref;
    Button locationButton;
    public SettingsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        spinnerLang = view.findViewById(R.id.spinner_languages_settings);
        spinnerLang.setOnItemSelectedListener(this);
        locationButton = view.findViewById(R.id.defaultLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                i.putExtra(Messages.groupID, Messages.settingsPlaceHolder);
                i.putExtra(Messages.meetingID,Messages.settingsPlaceHolder);
                i.putExtra(Messages.ADMIN,Messages.settingsPlaceHolder);
                GlobalBundle.getInstance().putAll(i.getExtras());
                startActivityForResult(i, 1);
            }
        });
        setUpLang();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = new FirebaseReference();
        ref.select("users").select(userId).get(User.class, new Consumer<User>() {
            @Override
            public void accept(User user) {
                SettingsFragment.this.user = user;
                MeetingLocation favoriteLocation = user.getFavoriteLocation();
                if(favoriteLocation == null){
                    favoriteLocation = MapsHelper.ROLEX_LOCATION;
                }
                locationButton.setText("Default Location: " + favoriteLocation.toString());
            }
        });

        return view;
    }

    void setUpLang() {
        //Language spinner
        List<String> languagesList = Arrays.asList("\uD83C\uDDEC\uD83C\uDDE7","\uD83C\uDDEB\uD83C\uDDF7","\uD83C\uDDE9\uD83C\uDDEA","\uD83C\uDDEE\uD83C\uDDF9");
        ArrayAdapter<String> dataAdapterLanguages = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, languagesList);
        dataAdapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(dataAdapterLanguages);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.spinner_languages_settings:
                user.setFavoriteLanguage(parent.getItemAtPosition(position).toString());
                ref.select("users").select(SettingsFragment.this.userId).setVal(user);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent d) {
        super.onActivityResult(requestCode, resultCode, d);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle data = GlobalBundle.getInstance().getSavedBundle();
            MeetingLocation meetingLocation = new MeetingLocation(
                    data.getString(Messages.LOCATION_TITLE),
                    data.getString(Messages.ADDRESS),
                    data.getDouble(Messages.LATITUDE, 0),
                    data.getDouble(Messages.LONGITUDE, 0)
            );
            user.setFavoriteLocation(meetingLocation);
            ref.select(Messages.FirebaseNode.USERS).select(userId).setVal(user);
        }
    }
}
