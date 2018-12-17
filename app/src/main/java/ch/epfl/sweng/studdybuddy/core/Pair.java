package ch.epfl.sweng.studdybuddy.core;

/**
 * A class representing a pair of two string valures
 */
public class Pair {
    String key;
    String value;

    public Pair(){}

    public Pair(String key, String value){
        this();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
