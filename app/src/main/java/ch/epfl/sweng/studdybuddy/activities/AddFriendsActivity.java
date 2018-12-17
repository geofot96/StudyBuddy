package ch.epfl.sweng.studdybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.tools.AdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.tools.ParticipantAdapter;
import ch.epfl.sweng.studdybuddy.tools.RecyclerAdapterAdapter;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.onClickAddUser;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.resetSelectViews;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.updateClickableUsers;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.showDropdown;

public class AddFriendsActivity extends AppCompatActivity {
    static ReferenceWrapper fireBase;
    static List<User> friendsDb = new ArrayList<>();
    List<String> userNames = new ArrayList<>();
    static AutoCompleteTextView autocompleteFriends;
    public static final List<User> friendSelection = new ArrayList<>();
    static ArrayAdapter<String> adapter;
    String uId;
    static Button addButton;
    private MetaGroupAdmin metaGroupAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireBase = new FirebaseReference();
        metaGroupAdmin = new MetaGroupAdmin(fireBase);
        setContentView(R.layout.activity_find_friends);
        setUpButtons();
        setUpSelectedFriends();
        setUpAutoComplete();
        setUpDb();
    }

    private void setUpButtons() {
        Intentable i = new Intentable(this, new Intent(this, NavigationActivity.class));
        addButton = findViewById(R.id.btn_add);
        User currentUser = ((StudyBuddy) AddFriendsActivity.this.getApplication()).getAuthendifiedUser();
        uId = currentUser.getUserID().getId();
        addButton.setOnClickListener(metaGroupAdmin.updateFriendsOnDone(uId, friendSelection, metaGroupAdmin, i));
    }

    private void setUpAutoComplete() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, userNames);
        autocompleteFriends = findViewById(R.id.friendsComplete);
        autocompleteFriends.setAdapter(adapter);
        autocompleteFriends.setOnClickListener(showDropdown(autocompleteFriends));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        autocompleteFriends.setOnItemClickListener(onClickAddUser(friendSelection, friendsDb, resetSelectViews(addButton, autocompleteFriends, imm)));
    }

    private void setUpSelectedFriends() {
        final RecyclerView selectedFriends = findViewById(R.id.friends_set);
        RecyclerView.Adapter friendsAdapter = new ParticipantAdapter(friendSelection);
        selectedFriends.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        selectedFriends.setAdapter(friendsAdapter);
        AdapterAdapter adapterC = new RecyclerAdapterAdapter(friendsAdapter);
        metaGroupAdmin.addListenner(adapterC);
        metaGroupAdmin.addListenner(updateClickableUsers(addButton, friendSelection));
    }

    private void setUpDb() {
        metaGroupAdmin.addListenner(new ArrayAdapterAdapter(adapter));
        metaGroupAdmin.fetchUser(friendsDb);
        metaGroupAdmin.fetchUserNames(userNames);
    }
}
