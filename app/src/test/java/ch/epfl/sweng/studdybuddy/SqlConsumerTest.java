package ch.epfl.sweng.studdybuddy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.sql.DAOs.SqlConsumers;

import static junit.framework.TestCase.assertEquals;


public class SqlConsumerTest {

    @Test
    public void clearAndFillTest(){
        List<User> users =  new ArrayList<>();
        List<User> u = new ArrayList<>();
        users.add(new User("b", new ID<>("c")));
        u.add(new User("a", new ID<>("c")));
        SqlConsumers.clearAndFill(users).accept(u);
        assertEquals(users,u);
    }
}
