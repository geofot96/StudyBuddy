package ch.epfl.sweng.studdybuddy.sql;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;

public interface LocalDBWrapper {

     List<User> get(ID<User> userId);
     void insert(User user);
}
