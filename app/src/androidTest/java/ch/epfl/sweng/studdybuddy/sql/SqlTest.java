package ch.epfl.sweng.studdybuddy.sql;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ch.epfl.sweng.studdybuddy.activities.group.GroupActivity;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.sql.DAOs.UserDAO;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SqlTest {
    private UserDAO mUserDao;
    private SqlDB mDb;

    @Rule
    public IntentsTestRule<GroupActivity>  mManualRule =
            new IntentsTestRule<>(GroupActivity.class, false, false);

    @Before
    public void createDb() {
        Context context = mManualRule.getActivity().getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, SqlDB.class).build();
        mUserDao = SqlDB.getInstance(context).userDAO();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeAndReadUser() throws Exception {
        User user = new User("a", new ID<User>("1"));
        mUserDao.insert(user);
        assertTrue(mUserDao.getAll().size() == 1);
        assertTrue(mUserDao.get(new ID<User>("1")).equals(user));
        mUserDao.delete(new ID<>("1"));
        assertTrue(mUserDao.getAll().size() ==0);
    }
}