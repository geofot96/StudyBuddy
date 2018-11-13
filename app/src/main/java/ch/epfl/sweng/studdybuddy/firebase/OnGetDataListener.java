package ch.epfl.sweng.studdybuddy.firebase;

import com.google.firebase.database.DataSnapshot;

/**
 * This interface is used to listen a single event on the database
 * in a synchronously way.
 */
public interface OnGetDataListener {
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
