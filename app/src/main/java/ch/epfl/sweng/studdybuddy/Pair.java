package ch.epfl.sweng.studdybuddy;

public class Pair<A,B> {
    A key;
    B value;

    public Pair(){}

    public Pair(A key, B value){
        this.key = key;
        this.value = value;
    }

    public A getKey() {
        return key;
    }

    public B getValue() {
        return value;
    }


    public void setKey(A key) {
        this.key = key;
    }

    public void setValue(B value) {
        this.value = value;
    }
}
