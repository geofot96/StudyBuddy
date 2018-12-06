package ch.epfl.sweng.studdybuddy.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.Intentable;

public final class CourseSelectController {
    private CourseSelectController() {
        throw new IllegalStateException();
    }
    public static View.OnClickListener updateCoursesOnDone(User currentUser, List<String> courseSelection, MetaGroupAdmin mga, Intentable mother) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mga.putAllCourses(courseSelection, currentUser.getUserID().getId());
                mother.launch();
            }
        };
    }


}
