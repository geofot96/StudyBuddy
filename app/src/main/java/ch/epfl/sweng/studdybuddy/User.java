package ch.epfl.sweng.studdybuddy;

import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private @DummyCourses.Section String Section;
    //private Schedule schedule;
    private ArrayList<Group> currentGroups;

    public User(String id, String name, String section, ArrayList<Group> currentGroups) {
        this.id = id;
        this.name = name;
        Section = section;
        this.currentGroups = currentGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public ArrayList<Group> getCurrentGroups() {
        return currentGroups;
    }

    public void setCurrentGroups(ArrayList<Group> currentGroups) {
        this.currentGroups = currentGroups;
    }
}
