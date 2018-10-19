package ch.epfl.sweng.studdybuddy;

import java.util.UUID;

public class ID<T> {
    private String id;

    public ID() {}

    public ID(String id){
        this.id = id;
    }

    //public String getID(){ return id;}

    public String getId() { return id; }

    public void setId(String id) {this.id = id;}

    public ID copy() { return new ID(id);}

    @Override
    public int hashCode(){ return id.hashCode();}

    @Override
    public String toString(){ return this.id;}
}
