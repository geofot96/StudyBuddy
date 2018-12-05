package ch.epfl.sweng.studdybuddy.sql.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getAll();

  /*  @Query("SELECT * FROM user WHERE userID = :userId")
    List<User> get(ID<User> userId);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM user WHERE userID = :id")
    void delete(ID<User> id );

    @Query("DELETE FROM user")
    void clear();

}