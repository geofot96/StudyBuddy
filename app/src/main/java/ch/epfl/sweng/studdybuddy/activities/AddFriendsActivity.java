package ch.epfl.sweng.studdybuddy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import ch.epfl.sweng.studdybuddy.tools.AdapterConsumer;
import ch.epfl.sweng.studdybuddy.tools.ArrayAdapterAdapter;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.onClickAddCourse;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.resetSelectViews;
import static ch.epfl.sweng.studdybuddy.controllers.CourseSelectController.updateCoursesOnDone;
import static ch.epfl.sweng.studdybuddy.util.ActivityHelper.showDropdown;

public class AddFriendsActivity extends AppCompatActivity {
    static ReferenceWrapper firebase;
    static List<String> friendsDB;
    static AutoCompleteTextView autocompleteFriends;
    public static final List<String> friendSelection = new ArrayList<>();
    public static ArrayAdapter<String> adapter;
    public static ArrayAdapter<String> adapterFriends;
    String uId;
    static Button addButton;
    private MetaGroupAdmin mga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        firebase  = new FirebaseReference();
        mga = new MetaGroupAdmin(firebase);
        setContentView(R.layout.activity_find_friends);
        setUpButtons();
       // setUpSelectedCourses();
        setUpDb(setUpAutoComplete());
    }

    private void setUpButtons() {
        Intentable i = new Intentable(this, new Intent(this, NavigationActivity.class));
        addButton = findViewById(R.id.btn_add);
        User currentUser = ((StudyBuddy) AddFriendsActivity.this.getApplication()).getAuthendifiedUser();
        uId = currentUser.getUserID().getId();
        addButton.setOnClickListener(updateCoursesOnDone(currentUser, friendSelection, mga, i));
    }

    private ArrayAdapter<String> setUpAutoComplete(){
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, friendsDB);
        autocompleteFriends = (AutoCompleteTextView) findViewById(R.id.friendsComplete);
        autocompleteFriends.setAdapter(adapter);
        autocompleteFriends.setOnClickListener(showDropdown(autocompleteFriends));
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        autocompleteFriends.setOnItemClickListener(onClickAddCourse(friendSelection, resetSelectViews(addButton, autocompleteFriends, imm)));
        return adapter;
    }

//    private void setUpSelectedFriends() {
//        final RecyclerView selectedFriends = (RecyclerView) findViewById(R.id.friends_set);
//        RecyclerView.Adapter friendsAdapter = new CourseAdapter(friendSelection);
//        selectedFriends.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        selectedFriends.setAdapter(friendsAdapter);
//        AdapterAdapter adapterC = new RecyclerAdapterAdapter(friendsAdapter);
//        mga.addListenner(adapterC);
//        mga.addListenner(updateClickable(addButton, friendSelection));
//        //TODO make a func to get friends
//        mga.getUserCourses(uId, friendSelection);
//
//    }
    private void setUpDb(ArrayAdapter<String> adapter) {
        firebase.select("users").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, friendsDB, new ArrayAdapterAdapter(adapter)));
    }
}
