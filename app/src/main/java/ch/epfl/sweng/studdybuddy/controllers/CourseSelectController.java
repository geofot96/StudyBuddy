package ch.epfl.sweng.studdybuddy.controllers;

import android.content.ClipData;
import android.os.health.SystemHealthManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.firebase.MetaGroupAdmin;
import ch.epfl.sweng.studdybuddy.tools.Consumer;
import ch.epfl.sweng.studdybuddy.tools.Holder;
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

    public static ItemTouchHelper deleteCourseOnSwipe(List<String> courseSelection, Button doneButton, RecyclerView.Adapter adapter) {
        return new ItemTouchHelper(deleteCourseOnSwipeCB(courseSelection, doneButton, adapter));
    }

    public static ItemTouchHelper.SimpleCallback deleteCourseOnSwipeCB(List<String> courseSelection, Button doneButton, RecyclerView.Adapter adapter) {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1)
            {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i)
            {
                onSwiped_(courseSelection, doneButton, adapter ,(Holder)viewHolder);

            }

        };
    }
    public static void onSwiped_(List<String> courseSelection, Button doneButton, RecyclerView.Adapter adapter, Holder cc) {
        courseSelection.remove(courseSelection.indexOf(cc.get()));
        adapter.notifyDataSetChanged();
        doneButton.setEnabled(courseSelection.size() == 0 ? false : true);
    }

    public static AdapterView.OnItemClickListener onClickAddCourse(List<String> courseSelection, Consumer<String> callback) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String textInput = parent.getAdapter().getItem(position).toString();
                if(!courseSelection.contains(textInput)) {
                    courseSelection.add(textInput);
                    callback.accept(textInput);
                }
            }
        };
    }

    public static Consumer<String> resetSelectViews(Button doneButton, AutoCompleteTextView autocomplete, InputMethodManager imm) {
        return new Consumer<String>() {
            @Override
            public void accept(String s) {
                doneButton.setEnabled(true);
                autocomplete.setText("");
                imm.hideSoftInputFromWindow(autocomplete.getWindowToken(), 0);
            }
        };
    }
}
