package ch.epfl.sweng.studdybuddy.core;

public class Pair {
    String key;
    String value;

    public Pair(){}

    public Pair(String key, String value){
        this();

        if(key == null) {
            throw new IllegalArgumentException("Cannot instantiate a pair with a null key");
        }else if (value == null){
            throw new IllegalArgumentException("Cannot instantiate a pair with null value");
        }
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
