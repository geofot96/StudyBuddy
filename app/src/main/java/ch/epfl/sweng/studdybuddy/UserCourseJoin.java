package ch.epfl.sweng.studdybuddy;

import java.util.UUID;

public class UserCourseJoin {

    private ID<UserCourseJoin> id;
    private ID<Course> courseID;
    private ID<User> userID;


    public UserCourseJoin(){}

    public UserCourseJoin(ID<UserCourseJoin> id, ID<Course> courseID, ID<User> userID) {
        this.id = id;
        this.courseID = courseID.copy();
        this.userID = userID.copy();
    }

    public UserCourseJoin(String courseID, String userID) {
        setCourseID(courseID);
        setUserID(userID);
        this.id = new ID<UserCourseJoin>(Integer.toString(hashCode()));

    }
    public ID<Course> getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = new ID<Course>(courseID);
    }

    public ID<User> getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = new ID<User>(userID);
    }


    public ID<UserCourseJoin> getId() {
        return id;
    }

    @Override
    public int hashCode(){
        return courseID.hashCode() + userID.hashCode();
    }



}
