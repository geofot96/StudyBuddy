package ch.epfl.sweng.studdybuddy.sql;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ch.epfl.sweng.studdybuddy.sql.DAOs.UserDAO;

@Database(entities = {ch.epfl.sweng.studdybuddy.core.User.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class SqlDB extends RoomDatabase{

        public abstract UserDAO userDAO();

}
