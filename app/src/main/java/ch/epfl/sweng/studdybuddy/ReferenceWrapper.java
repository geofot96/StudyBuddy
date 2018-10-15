package ch.epfl.sweng.studdybuddy;

import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

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

    <T> void get(Class<T> type, Consumer<T> callback);

    /**
     * Returns an iterator of the current level
     * @return an iterator
     */
     <T> void getAll(Class<T> type, Consumer<List<T>> callback);

     static <T> Consumer<List<T>> adapterConsumer(Class<T> type, List<T> set, RecyclerView.Adapter adapter) {
         return new Consumer<List<T>>() {
             @Override
             public void accept(List<T> groups) {
                 groups.removeAll(set);
                 set.addAll(groups);
                 adapter.notifyDataSetChanged();
             }
         };
     }
}
