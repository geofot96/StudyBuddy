package ch.epfl.sweng.studdybuddy.sql;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import ch.epfl.sweng.studdybuddy.core.ID;
import ch.epfl.sweng.studdybuddy.core.User;
import ch.epfl.sweng.studdybuddy.services.meeting.MeetingLocation;

public class Converter {
    public Converter(){}
    //Commented the converters that are not used for the moment but could be in the future
/*
    @TypeConverter
    public static  ID<Course> CourseIDfromString(String  id){
        Type type = new TypeToken<ID<Course>>() {
        }.getType();
        return new Gson().fromJson(id, type);
    }
    @TypeConverter
    public static  String StringfromCourseID(ID<Course> id){
        return new Gson().toJson(id);
    }
*/
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
    /*
    @TypeConverter
    public static  ID<Group> GroupIDfromString(String  id){
        Type type = new TypeToken<ID<Group>>() {
        }.getType();
        return new Gson().fromJson(id, type);
    }
    
    @TypeConverter
    public static  String StringfromGroupID(ID<Group> id){
        return new Gson().toJson(id);
    }
    @TypeConverter
    public static  ID<Meeting> MeetingIDfromString(String  id){
        Type type = new TypeToken<ID<Meeting>>() {
        }.getType();
        return new Gson().fromJson(id, type);
    }
    @TypeConverter
    public static  String StringfromMeetingID(ID<Meeting> id){
        return new Gson().toJson(id);
    }


    @TypeConverter
    public static  ID<Calendar> CalendarIDfromString(String  id){
        Type type = new TypeToken<ID<Meeting>>() {
        }.getType();
        return new Gson().fromJson(id, type);
    }
    @TypeConverter
    public static  String StringfromCalendarID(ID<Calendar> id){
        return new Gson().toJson(id);
    }


    @TypeConverter
    public static  ID<MeetingLocation> IDfromString(String  id){
        Type type = new TypeToken<ID<MeetingLocation>>() {
        }.getType();
        return new Gson().fromJson(id, type);
    }
    @TypeConverter
    public static  String fromID(ID<MeetingLocation> id){
        return new Gson().toJson(id);
    }
*/
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
