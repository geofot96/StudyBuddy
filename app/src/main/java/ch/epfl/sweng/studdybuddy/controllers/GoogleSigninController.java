package ch.epfl.sweng.studdybuddy.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.CourseSelectActivity;
import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.core.Account;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.FirebaseReference;
import ch.epfl.sweng.studdybuddy.firebase.ReferenceWrapper;
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
                final ID<User> userID = new ID<>(acct.getId());
                Intentable destination;
                if(user == null) { //create a new user and put in db
                    app.setAuthendifiedUser(new User(acct.getDisplayName(), userID));
                    app.disableTravis();
                    fb.select("users").select(userID.getId()).setVal(app.getAuthendifiedUser());
                    destination = new Intentable(context, CourseSelectActivity.class);
                }
                else {
                    app.setAuthendifiedUser(user);
                    destination = new Intentable(context, NavigationActivity.class);
                }
                destination.launch();
            }
        };
    }

    public static ValueEventListener fetchUserAndStart(Account acct, StudyBuddy app, Context ctx) {
        return fetchUserCallback(new FirebaseReference(), acct, app, ctx);
    }


    public static Consumer<List<User>> fetchUserAndStartConsumer(Account acct, StudyBuddy app, Context ctx){
        return new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> users) {
                if(users != null && users.size()> 0){
                    app.setAuthendifiedUser(users.get(0));
                    ctx.startActivity(new Intent(ctx, CourseSelectActivity.class));
                    //finish();
                }else {
                    fetchUserAndStart(acct, app, ctx);
                }
            }
        };
    }
}
