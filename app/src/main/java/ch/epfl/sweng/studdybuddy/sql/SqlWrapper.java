package ch.epfl.sweng.studdybuddy.sql;

import android.content.Context;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

/**
 * Wrapper around SQL
 * Encapsulates the different access methods
 * On a GET operation, the thread body is given by a Consumer
 */
public class SqlWrapper {

    private final String TAG = "SqlWrapper";
    private SqlDB sql;
    public SqlWrapper(Context ctx){
        sql = SqlDB.getInstance(ctx);
    }


    public Thread  getAllUsers(Consumer<List<User>> consumer){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.accept(sql.userDAO().getAll());
            }
        });
        t.start();
        return t;
    }

    public Thread insertUser(User user){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sql.userDAO().insert(user);
            }
        });
        t.start();
        return t;
    }

    public Thread getUser(String userID, Consumer<List<User>> consumer){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> user = sql.userDAO().get(new ID<>(userID));
                if(user.size() == 1) {
                    consumer.accept(user);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread delete(String id){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sql.userDAO().delete(new ID<>(id));
            }
        });
        t.start();
        return t;
    }

    public Thread clearUsers(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                sql.userDAO().clear();
            }
        });
        t.start();
        return t;
    }

}
