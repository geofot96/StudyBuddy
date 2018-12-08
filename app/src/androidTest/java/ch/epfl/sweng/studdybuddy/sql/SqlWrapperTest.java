package ch.epfl.sweng.studdybuddy.sql;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SqlTest {
    private SqlWrapper sql;
    List<User> users;
    @Rule
    public IntentsTestRule<NavigationActivity>  mManualRule =
            new IntentsTestRule<>(NavigationActivity.class);

    @Before
    public void createDb() {
        Context context = mManualRule.getActivity().getApplicationContext();
        new Converter();
        sql = new SqlWrapper(context);
        users = Arrays.asList(new User("a", new ID<User>("1")), new User("b", new ID<User>("2")));

    }

    @Test
    public void writeAndReadUserTest() throws InterruptedException {

        sql.clearUsers();
        sleep(500);
        sql.getAllUsers(assertSizeConsumer(0));
        sleep(500);
        sql.insertUser(users.get(0));
        sql.insertUser(users.get(1));
        sleep(500);
        sql.getAllUsers(assertSizeConsumer(2));
        sleep(500);
        sql.getUser(users.get(0).getUserID().getId(), assertEqualsConsumer(users, 0));
        sql.getUser(users.get(1).getUserID().getId(), assertEqualsConsumer(users, 1));
        sleep(500);
        sql.clearUsers();
        sleep(500);
        sql.getAllUsers(assertSizeConsumer(0));
        sleep(500);
    }


    @Test
    public void deleteUserTest() throws InterruptedException {

        sql.clearUsers();
        sql.insertUser(users.get(0));
        sleep(500);
        sql.getAllUsers(assertSizeConsumer(1));
        sleep(500);
        sql.delete(users.get(0).getUserID().getId());
        sleep(500);
        sql.getAllUsers(assertSizeConsumer(0));
        sleep(500);
    }


    private void sleep(int ms) throws InterruptedException {
        Thread.sleep(ms);
    }


    private Consumer<List<User>> assertSizeConsumer(int size){
        return new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> users) {
                int i = 1 ;

                assertTrue(users.size() == size);
            }
        };
    }

    private Consumer<List<User>> assertEqualsConsumer(List<User> u, int index){
        return new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> user) {
                assertEquals(u.get(index), user.get(0));
            }
        };
    }

}