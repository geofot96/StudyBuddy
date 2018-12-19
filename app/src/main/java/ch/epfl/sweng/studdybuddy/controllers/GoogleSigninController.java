package ch.epfl.sweng.studdybuddy.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.CourseSelectActivity;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.core.Account;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
import ch.epfl.sweng.studdybuddy.sql.SqlWrapper;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Intentable;
import ch.epfl.sweng.studdybuddy.util.StudyBuddy;

public final class GoogleSigninController {
    private GoogleSigninController() {}
    public static ValueEventListener fetchUserCallback(ReferenceWrapper fb, Account acct, StudyBuddy app, Context context) {
        return fb.select("users").select(acct.getId()).get(User.class, callbackUserFetch(fb, acct, app, context));
    }

    public static Consumer<User> callbackUserFetch(ReferenceWrapper fb, Account acct, StudyBuddy app, Context context) {
        return new Consumer<User>() {
            @Override
            public void accept(User user) {
                if(acct != null) {
                    Toast.makeText(context, "Welcome " + acct.getDisplayName(), Toast.LENGTH_SHORT).show();

                    final ID<User> userID = new ID<>(acct.getId());
                    Intentable destination;
                    if (user == null) { //create a new user and put in db
                        app.setAuthendifiedUser(new User(acct.getDisplayName(), userID));
                        app.disableTravis();
                        fb.select("users").select(userID.getId()).setVal(app.getAuthendifiedUser());
                        (new SqlWrapper(context)).insertUser(app.getAuthendifiedUser());
                        destination = new Intentable(context, CourseSelectActivity.class);
                    } else {
                        app.setAuthendifiedUser(user);
                        (new SqlWrapper(context)).insertUser(user);
                        destination = new Intentable(context, NavigationActivity.class);
                    }
                    destination.launch();
                }else{
                    //appears only when the user isn't connected to the app
                    Toast.makeText(context, "Please click to log in", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public static ValueEventListener fetchUserAndStart(Account acct, StudyBuddy app, Context ctx) {
        return fetchUserCallback(new FirebaseReference(), acct, app, ctx);
    }


    public static Consumer<List<User>>  fetchUserAndStartConsumer(Account acct, StudyBuddy app, Context ctx, boolean hasSignedOut){
        return new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> users) {
                // only one user in the sql db
                if(!hasSignedOut  && users != null && users.size() == 1){
                    for(User user : users){
                            app.setAuthendifiedUser(users.get(0));
                            ctx.startActivity(new Intent(ctx, CourseSelectActivity.class));
                            break;
                        }
                //multiple users in the sql db
                }else if(!hasSignedOut && acct != null && users!= null) {
                    for(User user : users){
                        if(user.getUserID().getId().equals(acct.getId())) {
                            app.setAuthendifiedUser(users.get(0));
                            ctx.startActivity(new Intent(ctx, CourseSelectActivity.class));
                            break;
                        }else {
                            fetchUserAndStart(acct, app, ctx);

                        }
                    }
                }
            }
        };
    }
}
