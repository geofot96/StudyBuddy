package ch.epfl.sweng.studdybuddy.core;

/**
 * class representing an identification document
 * id attribute is the String representation of identification document
 */
public class ID<T> {
    private String id;

    /**
     * Empty constructor for the ID class for the FireBase
     */
    public ID() {
    }

    /**
     * Constructor for the ID class
     *
     * @param id is string that will be set as identification document in current class
     */
    public ID(String id) {
        this();
        this.id = id;
    }

    /**
     * Constructor for the ID class
     *
     * @param id is another ID class and we set the same identification document as in id class
     */
    public ID(ID<T> id) {
        this.id = id.getId();
    }

    /**
     * Getter for the identification document
     *
     * @return the identification document
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for the identification document
     *
     * @param id the new id string to be set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Method that make a clone of ID class instance that call this method
     *
     * @return new ID class with the same id value as callee
     */
    public ID copy() {
        return new ID(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Overridden method which return an instance of this class in string representation
     *
     * @return the id field because it is string itself
     */
    @Override
    public String toString() {
        return this.id;
    }

    /**
     * Overridden method which check if two IDs are equal (all their fields are the same)
     *
     * @param o Object to be compared to this
     * @return true if the two objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ID)) {
            return false;
        }

        ID<T> id = (ID<T>) o;
        return id.getId().equals(((ID) o).getId());
    }
}
