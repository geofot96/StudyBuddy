package ch.epfl.sweng.studdybuddy.sql;

import android.content.Context;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.studdybuddy.activities.NavigationActivity;
import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.sql.DAOs.UserDAO;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SqlTest {
    private UserDAO mUserDao;
    private SqlDB mDb;

    @Rule
    public IntentsTestRule<NavigationActivity>  mManualRule =
            new IntentsTestRule<>(NavigationActivity.class);

    @Before
    public void createDb() {
        Context context = mManualRule.getActivity().getApplicationContext();
        mUserDao = SqlDB.getInstance(context).userDAO();
    }


    @Test
    public void writeAndReadUser() throws Exception {
        mUserDao.clear();
        User user = new User("a", new ID<User>("1"));
        mUserDao.insert(user);
        assertTrue(mUserDao.getAll().size() == 1);
        mUserDao.delete(new ID<>("1"));
        assertTrue(mUserDao.getAll().size() ==0);
    }
}