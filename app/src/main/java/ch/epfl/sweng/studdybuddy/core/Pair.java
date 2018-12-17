package ch.epfl.sweng.studdybuddy.core;

/**
 * A class representing a pair of two string valures
 */
public class Pair {
    String key;
    String value;

    /**
     * Empty constructor
     */
    public Pair(){}

    /**
     * Pair to be created with strings
     * @param key the key string
     * @param value the value sting
     */
    public Pair(String key, String value){
        this();
        this.key = key;
        this.value = value;
    }

    /**
     * Getter of the key string
     * @return the key of the pair
     */
    public String getKey() {
        return key;
    }
    /**
     * Getter of the value string
     * @return the value of the pair
     */
    public String getValue() {
        return value;
    }

    /**
     * Set a new key string
     * @param key the new key string
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Set a new value string
     * @param value the new value string
     */
    public void setValue(String value) {
        this.value = value;
    }
}
