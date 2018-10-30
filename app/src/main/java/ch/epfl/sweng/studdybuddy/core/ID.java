package ch.epfl.sweng.studdybuddy.core;

public class ID<T> {
    private String id;

    public ID() {}

    public ID(String id){
        this();
        this.id = id;
    }

    public ID(ID<T> id){
        this.id = id.getId();
    }

    //public String getID(){ return id;}

    public String getId() { return id; }

    public void setId(String id) {this.id = id;}

    public ID copy() { return new ID(id);}

    @Override
    public int hashCode(){ return id.hashCode();}

    @Override
    public String toString(){ return this.id;}

   /* @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ID)) {
            return false;
        }

        ID<T> id = (ID<T>) o;
        System.out.println(id.getId());
        return id.getId().equals(((ID) o).getId());
    }*/
}
