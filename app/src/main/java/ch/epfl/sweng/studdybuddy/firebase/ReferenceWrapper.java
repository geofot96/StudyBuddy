package ch.epfl.sweng.studdybuddy.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ch.epfl.sweng.studdybuddy.tools.Consumer;

public interface ReferenceWrapper {
    /**
     * Wraps the database instance
     */

    /**
     * Finds and returns the child with the given key
     * @param key the child key
     * @return the child reference
     */
    ReferenceWrapper select(String key);
    /**
     * Sets the object at current level
     * @param o the new object
     * @return a task
     */
    Task<Void> setVal(Object o);

    /**
     * Sets the value at this location to `null`
     * @return a task
     */
    Task<Void> clear();

    <T> ValueEventListener get(Class<T> type, Consumer<T> callback);

    /**
     * Returns an iterator of the current level
     * @return an iterator
     */
     <T> ValueEventListener getAll(Class<T> type, Consumer<List<T>> callback);

     ReferenceWrapper parent();
}
