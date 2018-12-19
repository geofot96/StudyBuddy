package ch.epfl.sweng.studdybuddy.sql.DAOs;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.tools.Consumer;

public class SqlConsumers {

    public static Consumer<List<User>> clearAndFill(List<User> userList){
        return new Consumer<List<User>>() {
            @Override
            public void accept(@Nullable List<User> users) {
                if(users != null && users.size() > 0){
                    userList.clear();
                    userList.addAll(users);
                    Log.i("USERS", users.toString());
                }
            }
        };
    }
}
