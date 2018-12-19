package ch.epfl.sweng.studdybuddy.sql;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import ch.epfl.sweng.studdybuddy.sql.DAOs.UserDAO;

/**
 * SQL local database based on room for Android
 */
@Database(entities = {ch.epfl.sweng.studdybuddy.core.User.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class SqlDB extends RoomDatabase{
        private static SqlDB db =null;

        public static SqlDB getInstance(Context  ctx){
                synchronized (SqlDB.class){
                        if(db == null){
                                db = Room.databaseBuilder(ctx, SqlDB.class, "StudyBuddy").build();
                        }
                        SqlDB.class.notifyAll();
                        return db;
                }
        }

        public abstract UserDAO userDAO();

}
