package ch.epfl.sweng.studdybuddy.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;

public class CourseSelectController {
    public static View.OnClickListener updateCoursesOnDone(User currentUser, List<String> courseSelection, MetaGroupAdmin mga, Intentable mother) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mga.putAllCourses(courseSelection, currentUser.getUserID().getId());
                mother.launch();
            }
        };
    }

    public static class Intentable {
        AppCompatActivity activity;
        Intent intent;
        public Intentable(AppCompatActivity activity, Intent intent) {
            this.activity = activity;
            this.intent = intent;
        }
        public void launch() {
            activity.startActivity(intent);
        }
    }
}
