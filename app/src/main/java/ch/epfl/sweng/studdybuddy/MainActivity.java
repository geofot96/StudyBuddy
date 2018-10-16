package ch.epfl.sweng.studdybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import static ch.epfl.sweng.studdybuddy.DummyCourses.MATHEMATICS;

public class MainActivity extends AppCompatActivity
{
    private AuthManager mAuth = null;
    static DummyCourses dummy = new DummyCourses();
    User u1 = new User("xxx@yyy.zzz", "User 1", MATHEMATICS, new ArrayList<Group>(),new ArrayList<User>());
    Group g1 = new Group(5, new Course(DummyCourses.getListOfCourses()[0]),DummyCourses.getListOfLanguages()[0], new ArrayList<User>());
    Group g2 = new Group(7, new Course(DummyCourses.getListOfCourses()[3]),DummyCourses.getListOfLanguages()[2], new ArrayList<User>());

    public static ArrayList<User> usersList1 = new ArrayList<>();
    public static ArrayList<Group> groupList1 = new ArrayList<>(); //made it public for create group which complained


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mSignOutBtn = findViewById(R.id.signout_btn);

        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(); //get signed out
            }
        });
        createFred();

    }

    @Override
    protected void onStart(){
        super.onStart();
        Account currentUser = getAuthManager().getCurrentUser();
        if(currentUser == null){
            signOut();
        }

    }


    private void signOut(){
        getAuthManager().logout().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(MainActivity.this, GoogleSignInActivity.class));
                    }
                });
    }

    AuthManager getAuthManager(){
        if (mAuth == null){
            mAuth = new FirebaseAuthManager(this, getString(R.string.default_web_client_id));
        }
        return mAuth;
    }

    public void createFred()
    {
        Button fred = (Button)findViewById(R.id.courseButton);
        final Intent intent = new Intent(this, CourseSelectActivity.class);

        fred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    public void goToProfileTabred(View view)
    {
        Intent intent = new Intent(this, ProfileTab.class);
        startActivity(intent);
    }

    public void gotoGroups(View view)
    {
        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }


}

