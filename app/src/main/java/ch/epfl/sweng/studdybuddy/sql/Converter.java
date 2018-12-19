package ch.epfl.sweng.studdybuddy.sql;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;


/**
 * Allows to store some specific types as JSON in the local database
 * and retrieve them as Java Objects
 */
public class Converter {

    public Converter(){}
    @TypeConverter
    public static  ID<User> UserIDfromString(String  id){
        Type type = new TypeToken<ID<User>>() {
        }.getType();
        return new Gson().fromJson(id, type);
    }
    @TypeConverter
    public static  String StringFromUserID(ID<User> id){
        return new Gson().toJson(id);
    }

    @TypeConverter
    public static MeetingLocation LocationfromString(String location){
        Type type = new TypeToken<MeetingLocation>(){}.getType();
        return new Gson().fromJson(location, type);
    }

    @TypeConverter
    public static String fromLocation(MeetingLocation location){
        return new Gson().toJson(location);
    }


}
