package ch.epfl.sweng.studdybuddy.sql.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE userID IN (:userIds)")
    List<User> loadAllByIds(ID<User>[] userIds);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);


}