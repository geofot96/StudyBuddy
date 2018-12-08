package ch.epfl.sweng.studdybuddy.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.activities.group.GlobalBundle;
import ch.epfl.sweng.studdybuddy.activities.group.MapsActivity;
import ch.epfl.sweng.studdybuddy.auth.FirebaseAuthManager;
import ch.epfl.sweng.studdybuddy.auth.GoogleSignInActivity;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroup;
import ch.epfl.sweng.studdybuddy.firebase.Metabase;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;
import ch.epfl.sweng.studdybuddy.sql.SqlWrapper;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.util.ActivityHelper;
import ch.epfl.sweng.studdybuddy.util.Language;
import ch.epfl.sweng.studdybuddy.util.MapsHelper;
import ch.epfl.sweng.studdybuddy.util.Messages;
import ch.epfl.sweng.studdybuddy.util.SettingsFragmentHelper;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

/**
 * A simple {@link Fragment} subclass.
 */

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinnerLang;
    String favoriteLanguage;
    User user;
    String uId;
    String gId;
    FirebaseReference ref;
    Metabase mb;
    Button locationButton;
    Button signout;
    Button applyButton;
    View view;
    MeetingLocation favoriteLocation = MapsHelper.ROLEX_LOCATION;
    SqlWrapper sql;
    StudyBuddy app;
    private TextView textDisplayLocation;

    public SettingsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
//         Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        app = ((StudyBuddy) getActivity().getApplication());
        user = app.getAuthendifiedUser();
        Log.i("D", "language is " + user.getFavoriteLanguage());
        ref = (FirebaseReference) getDB();
        uId = user.getUserID().getId();
        mb = new MetaGroup();
        sql = new SqlWrapper(this.getActivity());
        textDisplayLocation = view.findViewById(R.id.text_location_set_up);
        setUpUI();


        return view;
    }

    public ReferenceWrapper getDB(){
        return new FirebaseReference();
    }

    void setUpLang() {
        spinnerLang = view.findViewById(R.id.spinner_languages_settings);
        spinnerLang.setOnItemSelectedListener(this);

        FirebaseReference ref = new FirebaseReference();


        //Language spinner
        ArrayAdapter<String> dataAdapterLanguages = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, Language.languages);
        dataAdapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(dataAdapterLanguages);
        //select from local db first

        updateLanguage(user);
        ref.select(Messages.FirebaseNode.USERS).select(uId).get(User.class, SettingsFragmentHelper.updateLanguage(this));
    }


    public void updateLanguage(User user){
        String selectedLanguage = user.getFavoriteLanguage() != null ? user.getFavoriteLanguage() : Language.EN;
        spinnerLang.setSelection(Language.LanguageToInt(selectedLanguage));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.spinner_languages_settings:
                favoriteLanguage = (parent.getItemAtPosition(position).toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent d) {
        super.onActivityResult(requestCode, resultCode, d);
        MeetingLocation defaultLocation = ActivityHelper.meetingLocationFromBundle(requestCode, resultCode);

            if(defaultLocation != null) {
                favoriteLocation = defaultLocation;
                Log.i("TEST", favoriteLocation.toString());
                textDisplayLocation.setText("Default Location: " + defaultLocation.toString());
            }
    }

    public void setUpUI(){
        setUpLang();
        setUpLocation();
        setUpSignOut();
        SetUpApply();
    }

    public void SetUpApply(){
        applyButton = view.findViewById(R.id.btn_settings_apply);
        applyButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ref.select(Messages.FirebaseNode.USERS).select(SettingsFragment.this.uId).select("favoriteLanguage").setVal(favoriteLanguage);
               ref.select(Messages.FirebaseNode.USERS).select(uId).select("favoriteLocation").setVal(favoriteLocation);
               user.setFavoriteLanguage(favoriteLanguage);
               user.setFavoriteLocation(favoriteLocation);
               app.setAuthendifiedUser(user);
               sql.insertUser(user);
           }

        });


    }

    public void setUpLocation(){
        textDisplayLocation.setOnClickListener(new View.OnClickListener() {
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

        favoriteLocation = user.getFavoriteLocation() != null ? user.getFavoriteLocation() : favoriteLocation;
        textDisplayLocation.setText("Default Location: " + favoriteLocation.toString());

        mb.getUserAndConsume(uId, new Consumer<User>() {
            @Override
            public void accept(User user) {
                SettingsFragment.this.user = user;
                favoriteLocation = user.getFavoriteLocation();
                textDisplayLocation.setText("Default Location: " + favoriteLocation.toString());
            }
        });
    }
    public void setUpSignOut(){
        signout = view.findViewById(R.id.btn_sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsFragment.this.getContext(), GoogleSignInActivity.class);
                //If it's a travis test, don't logout from GoogleAuth
                if(uId.equals("Default")){
                    startActivity(intent);
                }else {
                    (new FirebaseAuthManager(SettingsFragment.this.getActivity(), uId)).logout().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(intent);
                        }
                    });
                }
                getActivity().finish();

            }
        });


    }
}
