package ch.epfl.sweng.studdybuddy;

import com.google.android.gms.tasks.Task;

interface ReferenceWrapper {

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
     * @return the object stored at the current level
     */
    Object get();


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
}
