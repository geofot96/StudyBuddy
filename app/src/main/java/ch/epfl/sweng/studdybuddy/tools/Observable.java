package ch.epfl.sweng.studdybuddy.tools;

public interface Observable {

    void addObserver(Observer observer);
    void notifyObservers();
}
