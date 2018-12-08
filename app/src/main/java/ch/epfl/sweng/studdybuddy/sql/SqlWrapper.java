package ch.epfl.sweng.studdybuddy.sql;

import android.content.Context;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

public class SqlWrapper {

    private final String TAG = "SqlWrapper";
    private SqlDB sql;
    public SqlWrapper(Context ctx){
        sql = SqlDB.getInstance(ctx);
    }


    public void  getAllUsers(Consumer<List<User>> consumer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                consumer.accept(sql.userDAO().getAll());
            }
        }).start();
    }

    public void insertUser(User user){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sql.userDAO().insert(user);
            }
        }).start();
    }

    public void getUser(String userID, Consumer<List<User>> consumer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> user = sql.userDAO().get(new ID<>(userID));
                if(user.size() == 1) {
                    consumer.accept(user);
                }
            }
        }).start();
    }

    public void delete(String id){
        new Thread((new Runnable() {
            @Override
            public void run() {
                sql.userDAO().delete(new ID<>(id));
            }
        })).start();
    }

    public void clearUsers(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sql.userDAO().clear();
            }
        }).start();
    }

}
