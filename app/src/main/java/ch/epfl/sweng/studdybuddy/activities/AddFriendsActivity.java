package ch.epfl.sweng.studdybuddy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.R;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;

public class AddFriendsActivity extends AppCompatActivity {
    static ReferenceWrapper firebase;
    static List<String> friendsDB;

    //List of selected friends
    public static final List<String> friendsSelection = new ArrayList<>();


    static AutoCompleteTextView autocompleteFriends;
    public static ArrayAdapter<String> adapterFriends;
    static Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        Intent other = getIntent();
        friendsDB = new ArrayList<>();
        friendsDB.add("Untitled");

//        setUpDb(setUpAutoComplete());
//        setUpButtons();
//        setUpSelectedCourses();

    }

//    private void setUpButtons() {
//        final Intent toMain = new Intent(this, NavigationActivity.class);
//        Button skipButton = findViewById(R.id.skipButton);
//        skipButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)  {
//                startActivity(toMain);
//            }
//        });
//        doneButton = findViewById(R.id.doneButton);
//        doneButton.setEnabled(false);
//        doneButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                User currentUser = ((StudyBuddy) CourseSelectActivity.this.getApplication()).getAuthendifiedUser();
//                new MetaGroupAdmin().putAllCourses(courseSelection, currentUser.getUserID().getId());
//                startActivity(toMain);
//            }
//        });
//    }

//    private ArrayAdapter<String> setUpAutoComplete(){
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, coursesDB);
//        autocomplete = (AutoCompleteTextView) findViewById(R.id.courseComplete);
//        autocomplete.setAdapter(adapter);
//        autocomplete.setOnClickListener(showDropdown(autocomplete));
//        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String textInput = parent.getAdapter().getItem(position).toString();
//                if(!courseSelection.contains(textInput)) { addCourse(textInput); }
//            }
//        });
//        return adapter;
//    }

//    private void setUpSelectedCourses() {
//
//        final RecyclerView selectedCourses = (RecyclerView) findViewById(R.id.coursesSet);
//        selectedCourses.setLayoutManager(new LinearLayoutManager(this));
//
//        selectedCourses.setAdapter(new CourseAdapter(courseSelection));
//       /*ItemTouchHelper mIth = new ItemTouchHelper(
//               new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
//                       ItemTouchHelper.RIGHT)
//               {
//                   @Override
//                   public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1)
//                   {
//                       return false;
//                   }
//
//                   @Override
//                   public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i)
//                   {
//                       Holder cc = (Holder) viewHolder;
//                       courseSelection.remove(courseSelection.indexOf(cc.get()));
//                       selectedCourses.getAdapter().notifyDataSetChanged();
//                       if(courseSelection.size() == 0)
//                           doneButton.setEnabled(false);
//                   }
//               });
//       mIth.attachToRecyclerView(selectedCourses);*/
//    }
//
//    private void setUpDb(ArrayAdapter<String> adapter) {
//        firebase = new FirebaseReference(FirebaseDatabase.getInstance().getReference());
//        firebase.select("courses").getAll(String.class, AdapterConsumer.adapterConsumer(String.class, coursesDB, new ArrayAdapterAdapter(adapter)));
//    }
//
//    private void addCourse(String course)
//    {
//        courseSelection.add(course);
//        //Dismiss KB
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(autocomplete.getWindowToken(), 0);
//        //reset search text
//        autocomplete.setText("");
//        doneButton.setEnabled(true);
//    }
//
//    private void removeCourse(String course)
//    {
//        courseSelection.remove(course);
//    }

}
