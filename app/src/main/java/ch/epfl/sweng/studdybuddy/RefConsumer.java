package ch.epfl.sweng.studdybuddy;

import ch.epfl.sweng.studdybuddy.Consumer;
import ch.epfl.sweng.studdybuddy.ReferenceWrapper;

public abstract class RefConsumer<T> implements Consumer<T> {

    ReferenceWrapper ref;

    public RefConsumer(ReferenceWrapper db){
        this.ref = db;
    }

    public RefConsumer(){
        this.ref = new FirebaseReference();
    }


}
